package com.sample.assignment.data.source.local;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class HttpResponseCachingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final String TAG = getClass().getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers headers = request.headers();

        Response session = buildFromCache(request);
        if (session != null) {
            return session;
        }

        Response response = chain.proceed(request);
        //  If cache is required, proceed and cache the result
        if (response.isSuccessful() && cacheRequired(headers)) {
            String key = request.url().toString();
            cache(key, response);
        }

        return response;
    }

    private String peekBody(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        // Buffer the entire body.
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        return buffer.clone().readString(UTF8);
    }

    private Response buildFromCache(Request request) {
        Headers headers = request.headers();
        String key = request.url().toString();

        boolean useCache = cacheRequired(headers) && !isManualRefresh(headers);
        if (useCache) {
            Log.d(TAG, "cache key: " + key);
            CacheRecord cached = CacheRecordSource.getInstance().get(key);
            Log.d(TAG, "from cached: " + cached);
            if (cached != null) {
                return build(request, cached);
            }
        }
        return null;
    }

    private boolean cacheRequired(Headers headers) {
        return headers.names().contains(Header.cache.name())
                && Boolean.valueOf(headers.values(Header.cache.name()).get(0));
    }

    private boolean isManualRefresh(Headers headers) {
        return headers.names().contains(Header.refresh.name())
                && Boolean.valueOf(headers.values(Header.refresh.name()).get(0));
    }

    private void cache(String key, Response response) throws IOException {
        ResponseBody responseBody = response.body();
        String json = peekBody(response);
        MediaType contentType = responseBody.contentType();
        String status = String.valueOf(response.code());

        CacheRecordSource.getInstance().saveCacheRecord(key, json, contentType.toString(), status, CacheRecordSource.SESSION_CACHE_EXPIRE);
        Log.d(TAG, "Save latest http response.");
    }

    private Response build(Request request, CacheRecord cacheRecord) {
        String mediaType;
        if (cacheRecord == null || cacheRecord.getMediaType() == null) {
            mediaType = CacheRecordSource.MEDIA_TYPE_DEFAULT;
        } else {
            mediaType = cacheRecord.getMediaType();
        }
        int status;
        if (cacheRecord == null || cacheRecord.getHttpStatus() == null) {
            status = CacheRecordSource.HTTP_STATUS_DEFAULT;
        } else {
            status = Integer.valueOf(cacheRecord.getHttpStatus());
        }

        Log.d(TAG, "http response built using cache data.");
        Response.Builder builder = new Response.Builder();

        builder.protocol(Protocol.HTTP_1_1)
                .request(request)
                .code(status).message("OK")
                .body(ResponseBody.create(MediaType.parse(mediaType), cacheRecord == null ? "null" : cacheRecord.getJson()));

        return builder.build();

    }

    public enum Header {
        cache, refresh
    }
}
