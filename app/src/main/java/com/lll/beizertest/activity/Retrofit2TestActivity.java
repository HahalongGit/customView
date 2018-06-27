package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;
import com.lll.beizertest.api.ServiceApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2 示例，解析
 */
public class Retrofit2TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2_test);

        //基本的使用
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://github.com/") //默认是用OkHttp3 作为网络请求框架
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        rxjaveRetrofit2Use(serviceApi);
        retrofit2Used(serviceApi);
    }

    /**
     * 直接使用
     * @param serviceApi
     */
    private void retrofit2Used(ServiceApi serviceApi) {
        serviceApi.login("username","password")
                .enqueue(new Callback<String>() {//ExecutorCallAdapterFactory 实现Call接口
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    private void rxjaveRetrofit2Use(ServiceApi serviceApi) {
        //Rxjave 和 Retrofit2结合使用
        serviceApi.getUserInfo("123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
