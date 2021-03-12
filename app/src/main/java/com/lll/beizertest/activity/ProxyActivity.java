package com.lll.beizertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lll.beizertest.R;

public class ProxyActivity extends AppCompatActivity {

    private static final String TAG = "ProxyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        Log.e(TAG, "ProxyActivity-onCreate");
    }
}
