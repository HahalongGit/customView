package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;
import com.lll.beizertest.http.HttpUtils;
import com.lll.beizertest.http.OkHttpEngine;

/**
 * 网络引擎测试
 */
public class HttpTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        HttpUtils.init(new OkHttpEngine());//初始化引擎
        HttpUtils.with(this)
                .url("")
                .addParam("","")

                .execute();

    }
}
