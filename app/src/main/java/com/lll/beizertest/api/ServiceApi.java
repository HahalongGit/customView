package com.lll.beizertest.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by longlong on 2018/6/27.
 *
 * @ClassName: ServiceApi
 * @Description: Retrofit2 api
 * @Date 2018/6/27
 */

public interface ServiceApi {

    //方法返回一个Observable
    @GET("user/userInfo")
    Observable<String> getUserInfo(@Query("userId")String userId);

    //Retrofit2 的方式，方法返回一个Call
    @POST("user/login")
    Call<String> login(@Field("userName")String userName,@Field("password")String password);

}
