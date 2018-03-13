package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;
import com.lll.beizertest.http.HttpCallback;
import com.lll.beizertest.http.HttpUtils;
import com.lll.beizertest.http.OkHttpEngine;
import com.lll.beizertest.model.Student;

/**
 * 网络引擎测试
 */
public class HttpTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        HttpUtils.init(new OkHttpEngine());//初始化引擎
        //请求的参数和路径都要放在jni中，防止反编译。
        HttpUtils.with(this)
                .url("")
                .addParam("","")
                .cache(true)
                .execute(new HttpCallback<Student>() {
                    @Override
                    public void onSuccess(Student result) {
                        //泛型实现，直接返回对象
                    }

                    @Override
                    public void onError(Exception message) {

                    }
                });

    }
}
