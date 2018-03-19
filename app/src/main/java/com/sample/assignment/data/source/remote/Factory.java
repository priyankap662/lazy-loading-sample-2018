package com.sample.assignment.data.source.remote;

import com.sample.assignment.BuildConfig;
import com.sample.assignment.R;
import com.sample.assignment.common.Callable;
import com.sample.assignment.common.Common;
import com.sample.assignment.data.DataService;
import com.sample.assignment.data.Feedback;
import com.sample.assignment.data.source.local.HttpResponseCachingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Factory {
    private static final String BASE_URL = "https://dl.dropboxusercontent.com";

    private static final String UNSUCCESSFUL_RESPONSE = "A system error has occurred";

    private static OkHttpClient httpClient;

    private static final HttpResponseCachingInterceptor HTTP_RESPONSE_CACHING_INTERCEPTOR
            = new HttpResponseCachingInterceptor();

    private static final HttpLoggingInterceptor HTTP_LOGGER_INTERCEPTOR
            = (BuildConfig.DEBUG)
            ? new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            : new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);

    private static final Retrofit RETROFIT_OPEN_AUTH = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient(HTTP_LOGGER_INTERCEPTOR, HTTP_RESPONSE_CACHING_INTERCEPTOR))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> void enqueue(Call<T> call, final Callable<T> success, final Callable<T> stale, final Callable<Throwable> error, final Callable<Boolean> received) {
        enqueue(call, new Feedback<T>() {
            @Override
            public void success(T t) {
                success.call(t);
            }

            @Override
            public void stale(T t) {
                stale.call(t);
            }

            @Override
            public void error(Throwable throwable) {
                error.call(throwable);
            }

            @Override
            public void received(boolean success) {
                received.call(success);
            }
        });
    }

    private static <T> void enqueue(Call<T> call, final Feedback<T> feedback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                if (response.isSuccessful()) {
                    feedback.success(response.body());
                    feedback.received(true);
                } else {
                    feedback.error(new Exception(UNSUCCESSFUL_RESPONSE));
                    feedback.received(false);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                feedback.error(throwable);
                feedback.received(false);
            }
        });
    }

    private static OkHttpClient httpClient(Interceptor... interceptors) {
        int readTimeOut = Common.context().getResources().getInteger(R.integer.read_timeout_seconds);
        int connectionTimeOut = Common.context().getResources().getInteger(R.integer.connection_timeout_seconds);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectionTimeOut, TimeUnit.SECONDS);

        for (Interceptor interceptor : interceptors) {
            builder.interceptors().add(interceptor);
        }
        httpClient = builder.build();
        return httpClient;
    }

    public static DataService dataService() {
        return RETROFIT_OPEN_AUTH.create(DataService.class);
    }

    public static OkHttpClient getHttpClient() {
        return httpClient;
    }
}
