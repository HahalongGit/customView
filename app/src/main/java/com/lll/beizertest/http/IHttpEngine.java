package com.lll.beizertest.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by longlong on 2018/2/26.
 *
 * @ClassName: IHttpEngine
 * @Description: 网络请求 引擎
 * @Date 2018/2/26
 */

public interface IHttpEngine {

    void get(Context context,String url, Map<String,Object> param, EngineCallback engineCallback);

    void post(Context context,String url, Map<String,Object> param,EngineCallback engineCallback);

    //上传文件
    //下载文件
    //,,.
}
