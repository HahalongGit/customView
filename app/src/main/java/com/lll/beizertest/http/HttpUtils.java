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

    /**
     * 请求url
     */
    private String mUrl;

    /**
     * 请求类型，默认是GET
     */
    private int mType = GET;

    /**
     * POST 请求类型
     */
    private static final int POST = 0X0011;

    /**
     * GET 请求类型
     */
    private static final int GET = 0X0010;

    /**
     * 参数集合
     */
    private Map<String,Object> mParam;

    /**
     * 回调参数,execute() 方法中直接设置，不需要添加一个链式调用方法
     */
    private EngineCallback mEngineCallback;

    private Context mContext;

    /**
     * 私有 构造方法
     * @param context
     */
    private HttpUtils(Context context) {
        this.mContext = context;
        mParam = new HashMap<>();
    }

    /**
     * 添加Context
     * @param context
     * @return
     */
    public static HttpUtils with(Context context){
        return new HttpUtils(context);
    }

    /**
     * 设置请求方式为POST
     * @return
     */
    public HttpUtils post(){
        mType = POST;
        return this;
    }

    /**
     * 设置请求方式为GET
     * @return
     */
    public HttpUtils get(){
        mType = GET;
        return this;
    }

    /**
     * 设置url
     * @param url
     * @return
     */
    public HttpUtils url(String url){
        mUrl = url;
        return this;
    }


    /**
     * 添加单个参数
     * @param key
     * @param value
     * @return
     */
    public HttpUtils addParam(String key,Object value){
        mParam.put(key,value);
        return this;
    }

    /**
     * 添加多个参数Map集合
     * @param params
     * @return
     */
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
            mIhttpEngine.post(mContext,mUrl,mParam,callback);
        }else if(mType==GET){
            mIhttpEngine.get(mContext,mUrl,mParam,callback);
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
     * 初始化一个自定义的引擎，调用的时候可以带，不带使用默认的
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
    public void get(Context context,String url, Map<String, Object> param, EngineCallback engineCallback) {
        //调用引擎的GET方法
        mIhttpEngine.get(context,url,param,engineCallback);
    }

    @Override
    public void post(Context context,String url, Map<String, Object> param, EngineCallback engineCallback) {
        //调用引擎的POST方法
        mIhttpEngine.post(context,url,param,engineCallback);
    }

    //其他的处理，可能有Delete 、UploadFile方法等，根据需求添加。

}
