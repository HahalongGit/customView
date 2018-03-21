package com.lll.beizertest.http;

import android.content.Context;

import com.lll.beizertest.db.DaoSupport;
import com.lll.beizertest.db.DaoSupportFactory;
import com.lll.beizertest.db.IDaoSupport;

import java.util.List;
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
    //3. 结合数据库实现缓存
    // 如果cache 时true ，每次执行的时候查询数据库，看有没有缓存，有缓存直接读取数据，然后返回，
    //然后请求网络数据，获取到数据后和保存在数据库中的进行比较，一致则不用保存新的。
    //不一致的保存新的数据，并且把新的数据跟新到界面。刷新。
    //注意的地方：以url作为要给key保存数据苦如果有问题，可以把url做一个类似MD5 加密的处理，然后保存，反正都是唯一的key。

    @Override
    public void get(boolean cache,Context context, String url, Map<String, Object> param, EngineCallback engineCallback) {
        //RequestBody requestBody = RequestBody.create(MediaType.parse(""),context);
        // okHttp 处理比较复杂
//        Request request = new Request.Builder()
//                .url(url)
//                .tag(context)
//                .post();
        if(cache){//是否需要缓存
            //需要缓存，拿缓存
            IDaoSupport<CacheData> cacheData  =DaoSupportFactory.getInstance().getDao(CacheData.class);
            List<CacheData> cacheData1 = cacheData.query() ; //根据url，查询制定URL的一条数据记录,这里没有些条件查询。
            //条件查询的结果，判断是否有值，有值则回调，没有值则请求网络。

            if(cacheData1.size()!=0){
                String resultJson = cacheData1.get(0).getResultJson();
            }
            //Http请求网络：
        }
    }

    @Override
    public void post(boolean cache,Context context,String url, Map<String, Object> param, EngineCallback engineCallback) {

    }
}
