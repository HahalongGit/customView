package com.lll.beizertest.http;

/**
 * Created by longlong on 2018/2/26.
 *
 * @ClassName: EngineCallback
 * @Description: 网络回调
 * @Date 2018/2/26
 */

public interface EngineCallback {

    //错误回调
    public void onError(Exception message);

    //正确回调
    public void onSuccess(String result);

    /**
     * 默认回调
     */
    public final EngineCallback DEFAULT_CALLBACK = new EngineCallback() {
        @Override
        public void onError(Exception message) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };

}
