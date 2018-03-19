package com.sample.assignment.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.Pair;

import com.sample.assignment.BuildConfig;
import com.sample.assignment.common.Common;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

class BitmapUtils {

    //prevent redirect loop on certain devices
    private static final CookieJar ALL_COOKIES = new JavaNetCookieJar(
            new CookieManager(null, CookiePolicy.ACCEPT_ALL));

    static Pair<Integer, Bitmap> decodeScaledBitmap(String url) throws IOException {

        final OkHttpClient.Builder builder = new OkHttpClient().newBuilder().cookieJar(ALL_COOKIES);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        OkHttpClient client = builder.build();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        int responseCode = response.code();
        if (!response.isSuccessful()) {
            return new Pair<>(responseCode, null);
        }

        byte[] bytes = response.body().bytes();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        return new Pair<>(responseCode, BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options));
    }

    static Bitmap decodeScaledBitmap(int imgRes, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeResource(Common.context().getResources(), imgRes, options);
    }
}
