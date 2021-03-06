package com.lll.beizertest.http;

import android.content.Context;
import android.renderscript.Element;

import java.util.Map;

/**
 * Created by longlong on 2018/2/26.
 *
 * @ClassName: IHttpEngine
 * @Description: 网络请求 引擎
 * @Date 2018/2/26
 */

public interface IHttpEngine  {


    /**
     * get 请求
     * @param context
     * @param url
     * @param param
     * @param engineCallback
     */
    void get(boolean cache,Context context,String url, Map<String,Object> param, EngineCallback engineCallback);

    /**
     * post 请求
     * @param context
     * @param url
     * @param param
     * @param engineCallback
     */
    void post(boolean cache,Context context,String url, Map<String,Object> param,EngineCallback engineCallback);

    //上传文件
    //下载文件
    //,,.
}
