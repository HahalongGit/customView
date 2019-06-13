package com.lll.beizertest.activity.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by longlong on 2019/6/12.
 *
 * @ClassName: BookProvider
 * @Description: ContentProvider 实现分析
 * @Date 2019/6/12
 */

public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";

    @Override
    public boolean onCreate() {
        Log.e(TAG, "onCreate--" + Thread.currentThread().getName());
        // 运行在主线程的方法
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query" + Thread.currentThread().getName());
        //运行在Binder线程池
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e(TAG, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "insert");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "update");
        return 0;
    }
}
