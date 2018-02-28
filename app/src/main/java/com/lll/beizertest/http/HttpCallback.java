package com.lll.beizertest.http;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by longlong on 2018/2/28.
 *
 * @ClassName: HttpCallback
 * @Description: 业务层的一Http个实现回调，用分层的架构中的业务处理中实现，不同的业务实现不同的回调
 * @Date 2018/2/28
 */

public abstract class HttpCallback<T> implements EngineCallback {
    /**
     * 处理公用的参数等业务数据
     * @param context
     * @param params
     */
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        //比如 请求头信息，token，拦截信息等。都在公共地方处理。添加完参数，提供一个方法，可实现，可不实现，共需要时处理
        onPreExecute();
    }

    //覆盖onSuccess方法
    @Override
    public void onSuccess(String result) {
        //上层实现泛型
        //获取T 类型HttpUtils.analysisClazzInfo(this)
        T dataResult = (T) new Gson().fromJson(result,HttpUtils.analysisClazzInfo(this));
        onSuccess(dataResult);
    }

    /**
     * 开始执行
     */
    protected void onPreExecute() {

    }

    public abstract void onSuccess(T result);


}
