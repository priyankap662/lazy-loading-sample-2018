/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.assignment.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.sample.assignment.R;
import com.sample.assignment.common.Common;

public class CacheRecordSource {

    private static final String TAG = CacheRecordSource.class.getName();

    static final long SESSION_CACHE_EXPIRE = Common.context().getResources().getInteger(R.integer.session_cache_seconds);
    static final String MEDIA_TYPE_DEFAULT = "application/json";
    static final int HTTP_STATUS_DEFAULT = 200;

    private static volatile CacheRecordSource INSTANCE;
    private static CacheRecordDao cacheRecordDao;

    private CacheRecordSource() {
        cacheRecordDao = ToDoDatabase.getInstance().cacheRecordDao();
    }

    static CacheRecordSource getInstance() {
        if (INSTANCE == null) {
            synchronized (CacheRecordSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CacheRecordSource();
                }
            }
        }
        return INSTANCE;
    }

    public CacheRecord get(@NonNull final String key) {
        return cacheRecordDao.get(key);
    }

    void saveCacheRecord(final String key, final String body, final String mediaType, final String status, final Long expire) {
        try {
            CacheRecord record = new CacheRecord(key, body, mediaType, status, expire);
            cacheRecordDao.insertCacheRecord(record);
        } catch (Exception e) {
            Log.e(TAG, "Failed to save to cache.", e);
        }
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
