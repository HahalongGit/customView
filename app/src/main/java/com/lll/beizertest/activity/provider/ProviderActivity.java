package com.lll.beizertest.activity.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;

/**
 * provider测试分析
 * 多进程下测试ContentProvider
 */
public class ProviderActivity extends AppCompatActivity {

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        String uri = "content://com.lll.beizertest.activity.provider";
        contentResolver = getContentResolver();

        contentResolver.query(Uri.parse(uri), null, null, null, null);
        contentResolver.query(Uri.parse(uri), null, null, null, null);
        contentResolver.query(Uri.parse(uri), null, null, null, null);

    }

}
