package com.lll.beizertest.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by longlong on 2018/2/26.
 *
 * @ClassName: EngineCallback
 * @Description: 地城封装 网络回调，共业务层实现
 * @Date 2018/2/26
 */

public interface EngineCallback {

    /**
     * 用户 请求的预处理，填写公共参数等，什么业务需要，什么业务实现，处理不同的业务参数，
     * @param context
     * @param params
     */
    public void onPreExecute(Context context, Map<String,Object> params);

    //错误回调
    public void onError(Exception message);

    //正确回调，这里不同泛型写返回结果，有些后台在成功和失败的时候返回的不一会，有时候是对象，有时候是String，底层不处理，留给业务层
    public void onSuccess(String result);

    /**
     * 默认回调
     */
    public final EngineCallback DEFAULT_CALLBACK = new EngineCallback() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception message) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };

}
