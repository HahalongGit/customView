package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;
import com.lll.beizertest.http.HttpCallback;
import com.lll.beizertest.http.HttpUtils;
import com.lll.beizertest.http.OkHttpEngine;
import com.lll.beizertest.model.Student;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络引擎测试
 */
public class HttpTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        httpUtilTest();

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("key","value")
                .build();
        Request request = new Request.Builder()
                .addHeader("","")
                .method("POST",formBody)
                .put(formBody)
                .cacheControl(CacheControl.FORCE_CACHE)
                .tag("")
                .url("").build();

        //添加拦截器等中间操作
        okHttpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        }).cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return null;
            }
        });

        try {
            okHttpClient.newCall(request).execute();// 同步执行
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 异步执行
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    private void httpUtilTest() {
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
