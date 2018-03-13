package com.lll.beizertest.http;

import android.content.Context;
import android.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
     * 是否缓存
     */
    private boolean mCache = false;

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
     * 是否缓存
     * @param cache
     * @return
     */
    public HttpUtils cache(boolean cache){
        //两个地方可以缓存
        //1.回调中
        //1.引擎中
        mCache = cache;
        return this;
    }

    /**
     * 执行方法
     * @param callback
     */
    public void execute(EngineCallback callback){
        //不能中间添加参数，影响其他地方业务调用。
        //如何处理调用前公共的一些参数？
        if(callback==null){
           callback = EngineCallback.DEFAULT_CALLBACK;
        }
        //调用预处理的方法，处理每个业务处理的公用参数
        callback.onPreExecute(mContext,mParam);
        if(mType==POST){
            mIhttpEngine.post(mCache,mContext,mUrl,mParam,callback);
        }else if(mType==GET){
            mIhttpEngine.get(mCache,mContext,mUrl,mParam,callback);
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
    public void get(boolean cache,Context context,String url, Map<String, Object> param, EngineCallback engineCallback) {
        //调用引擎的GET方法
        mIhttpEngine.get(cache,context,url,param,engineCallback);
    }

    @Override
    public void post(boolean cache,Context context,String url, Map<String, Object> param, EngineCallback engineCallback) {
        //调用引擎的POST方法
        mIhttpEngine.post(cache,context,url,param,engineCallback);
    }

    //其他的处理，可能有Delete 、UploadFile方法等，根据需求添加。


    /**
     * 解析一个类上的Class信息  ？？？
     * @param object
     * @return
     */
    public static Class<?> analysisClazzInfo(Object object){
        Type genType = object.getClass().getGenericSuperclass();
        Type params[] = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}
