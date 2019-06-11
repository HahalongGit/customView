package com.lll.beizertest.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;

/**
 * contentProvider
 */
public class ContentPorviderActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_porvider);


//        ContentUris.withAppendedId()
        ContentResolver contentResolver = getContentResolver();

        contentResolver.query(Uri.parse(""),null,null,null,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}
