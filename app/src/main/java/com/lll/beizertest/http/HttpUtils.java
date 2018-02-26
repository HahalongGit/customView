package com.lll.beizertest.http;

import android.content.Context;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlong on 2018/2/26.
 *
 * @ClassName: HttpUtils
 * @Description:
 * @Date 2018/2/26
 */

public class HttpUtils implements IHttpEngine {

    private String mUrl;

    private int mType = GET;

    private static final int POST = 0X0011;

    private static final int GET = 0X0010;

    private Map<String,Object> mParam;

    private EngineCallback mEngineCallback;

    private Context mContext;

    private HttpUtils(Context context) {
        this.mContext = context;
        mParam = new HashMap<>();
    }

    public static HttpUtils with(Context context){
        return new HttpUtils(context);
    }

    public HttpUtils post(){
        mType = POST;
        return this;
    }

    public HttpUtils get(){
        mType = GET;
        return this;
    }

    public HttpUtils url(String url){
        mUrl = url;
        return this;
    }

    public HttpUtils addParam(String key,Object value){
        mParam.put(key,value);
        return this;
    }

    public HttpUtils addParams(Map<String,Object> params){
        mParam.putAll(params);
        return this;
    }

    /**
     * 执行方法
     * @param callback
     */
    public void execute(EngineCallback callback){
        if(callback==null){
           callback = EngineCallback.DEFAULT_CALLBACK;
        }
        if(mType==POST){
            mIhttpEngine.post(mUrl,mParam,callback);
        }else if(mType==GET){
            mIhttpEngine.get(mUrl,mParam,callback);
        }
    }

    /**
     * 执行默认回调
     */
    public void execute(){
        //null 的时候写一个默认的回调
        execute(null);
    }
    //默认是Okhttp的引擎
    private static IHttpEngine mIhttpEngine = new OkHttpEngine();

    /**
     * 初始化一个自定义的引擎
     * @param iHttpEngine
     */
    public static void init(IHttpEngine iHttpEngine){
        mIhttpEngine = iHttpEngine;
    }

    /**
     * 每次调用的时候带引擎
     * @param iHttpEngine
     */
    public static void exChangeEngine(IHttpEngine iHttpEngine){
        mIhttpEngine = iHttpEngine;
    }

    @Override
    public void get(String url, Map<String, Object> param, EngineCallback engineCallback) {
        mIhttpEngine.get(url,param,engineCallback);
    }

    @Override
    public void post(String url, Map<String, Object> param, EngineCallback engineCallback) {
        mIhttpEngine.post(url,param,engineCallback);
    }
}
