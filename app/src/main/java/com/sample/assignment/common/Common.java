package com.sample.assignment.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Common {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @SuppressLint("StaticFieldLeak")
    private static Application application;

    private static class ThreadPoolHolder {
        static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    }

    private static class UIHandlerHolder {
        static final Handler uiHandler = new Handler(Looper.getMainLooper());
    }

    public static void init(Context context, Application application) {
        Common.context = context;
        Common.application = application;
    }

    public static Context context() {
        return context;
    }

    public static Application application() {
        return application;
    }

    public static ExecutorService getCachedThreadPool() {
        return ThreadPoolHolder.cachedThreadPool;
    }

    public static Handler getUIHandler() {
        return UIHandlerHolder.uiHandler;
    }
}
