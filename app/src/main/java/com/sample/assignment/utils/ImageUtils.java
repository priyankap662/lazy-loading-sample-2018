package com.sample.assignment.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.sample.assignment.common.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();
    private static LruCache<Integer, Bitmap> lruCache = new LruCache<>(42);
    private static final int DEFAULT_SUCCESS_CODE = 200;
    private static File cacheDir;

    public static Bitmap get(final String url) {
        return getBitmapWithRespCode(url).second;
    }

    private static Pair<Integer, Bitmap> getBitmapWithRespCode(final String url) {
        Pair<Integer, Bitmap> bitmapResponse = null;

        if (!TextUtils.isEmpty(url)) {
            Integer hash = hash(url, false);
            Bitmap bitmap = lruCache.get(hash);

            if (bitmap == null) {
                bitmap = fromDisk(hash);
                if (bitmap == null) {
                    bitmapResponse = fromUrl(url);

                } else {
                    bitmapResponse = new Pair<>(DEFAULT_SUCCESS_CODE, bitmap);
                }

            } else {
                bitmapResponse = new Pair<>(DEFAULT_SUCCESS_CODE, bitmap);
            }
        }

        return bitmapResponse;
    }

    public static Bitmap get(@DrawableRes int imageRes) {
        return get(imageRes, ImageSubSample.HALF);
    }

    public static Bitmap get(@DrawableRes int imageRes, ImageSubSample imageSubSample) {
        Bitmap bitmap = lruCache.get(imageRes);
        if (bitmap == null) {
            bitmap = BitmapUtils.decodeScaledBitmap(imageRes, imageSubSample.inSampleSize);
            lruCache.put(imageRes, bitmap);
        }
        return bitmap;
    }

    @SuppressWarnings("unused")
    public static boolean alreadyDownloaded(String url) {
        return file(hash(url, false)).exists();
    }

    private static Bitmap fromDisk(Integer hash) {
        File file = file(hash);
        if (file.exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                if (bitmap != null) {
                    lruCache.put(hash, bitmap);
                    return bitmap;
                }
            } catch (Throwable throwable) {
                Log.w(TAG, "Error decoding from disk.", throwable);
            }
        }
        return null;
    }

    private static Pair<Integer, Bitmap> fromUrl(String url) {
        try {
            Pair<Integer, Bitmap> bitmapResponse = download(url);
            if (bitmapResponse.second != null) {
                save(bitmapResponse.second, hash(url, false));
            }
            return bitmapResponse;
        } catch (Throwable throwable) {
            Log.e(TAG, "Unable to download image from " + url, throwable);
            return new Pair<>(0, null);
        }
    }

    private static Pair<Integer, Bitmap> download(String url) throws IOException {
        Pair<Integer, Bitmap> bitmapResponse = BitmapUtils.decodeScaledBitmap(url);
        Log.i(TAG, "Downloaded image from " + url);
        return bitmapResponse;
    }

    private static void save(@NonNull Bitmap bitmap, Integer hash) {
        try {
            // save to cache
            lruCache.put(hash, bitmap);
            // save to disk
            FileOutputStream out = new FileOutputStream(file(hash));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            Log.i(TAG, "Cached to disk " + hash);
        } catch (IOException e) {
            Log.e(TAG, "Unable to cached image " + hash, e);
        }
    }

    public static void delete(String url, boolean blur) {
        File file = ImageUtils.file(hash(url, blur));
        if (file.exists()) {
            file.delete();
        }
    }

    private static Integer hash(String url, boolean blur) {
        return (blur ? String.format("%s-b", url) : url).hashCode();
    }

    private static File file(Integer hash) {
        return new File(cacheDir(), hash.toString());
    }

    private static File cacheDir() {
        if (cacheDir == null) {
            cacheDir = Common.context().getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        }
        return cacheDir;
    }

    public enum ImageSubSample {
        HALF(2), QUARTER(4);

        private int inSampleSize;

        ImageSubSample(int inSampleSize) {
            this.inSampleSize = inSampleSize;
        }
    }
}
