package com.sample.assignment.data.source.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "CacheRecord")
public class CacheRecord {

    @PrimaryKey
    @NonNull
    private String key;

    @NonNull
    private String json;

    /**
     * Optional, used for http response caching only
     */
    @Nullable
    private String mediaType;

    /**
     * Optional, used for http response caching only
     */
    @Nullable
    private String httpStatus;

    /**
     * expire in seconds
     */
    @Nullable
    private Long expire;

    CacheRecord(final String key, final String json, final Long expire) {
        this(key, json, null, null, expire);
    }

    CacheRecord(@NonNull final String key, @NonNull final String json, @Nullable final String mediaType, @Nullable final String status, @Nullable final Long expire) {
        this.key = key;
        this.json = json;
        this.mediaType = mediaType;
        this.httpStatus = status;
        this.expire = expire;
    }

    @NonNull
    String getKey() {
        return key;
    }

    @NonNull
    String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Nullable
    Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    @Nullable
    String getMediaType() {
        return mediaType;
    }

    void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Nullable
    String getHttpStatus() {
        return httpStatus;
    }

    void setHttpStatus(@Nullable String httpStatus) {
        this.httpStatus = httpStatus;
    }
}