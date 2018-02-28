package com.lll.beizertest.http;

import android.content.Context;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by longlong on 2018/2/26.
 *
 * @ClassName: OkHttpEngine
 * @Description: 使用 OkHttp 实现网络引擎
 * @Date 2018/2/26
 */

public class OkHttpEngine implements IHttpEngine {

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    //问题：
    //1.如何处理公用的参数？分层的baseLisbrary 中不能包含业务处理数据？
    //2.每次回调要用json回调，怎么处理
    //3.

    @Override
    public void get(Context context, String url, Map<String, Object> param, EngineCallback engineCallback) {
        //RequestBody requestBody = RequestBody.create(MediaType.parse(""),context);
        // okHttp 处理比较复杂
//        Request request = new Request.Builder()
//                .url(url)
//                .tag(context)
//                .post();
    }

    @Override
    public void post(Context context,String url, Map<String, Object> param, EngineCallback engineCallback) {

    }
}
