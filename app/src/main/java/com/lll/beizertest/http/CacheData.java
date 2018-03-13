package com.lll.beizertest.http;

/**
 * Created by longlong on 2018/3/2.
 *
 * @ClassName: CacheData
 * @Description:
 * @Date 2018/3/2
 */

public class CacheData {

    /**
     * url
     */
    private String urlKey;

    /**
     * 后台返回的数据josn
     */
    private String resultJson;

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String mUrlKey) {
        this.urlKey = mUrlKey;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String mResultJson) {
        this.resultJson = mResultJson;
    }
}
