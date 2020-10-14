package com.lll.beizertest.proxy;

import android.util.Log;

/**
 * TODO:describe what the class or interface does.
 *
 * @author RunningDigua
 * @date 2020/10/14
 */
public class HelloServiceImpl implements HelloService {

    private static final String TAG = "HelloServiceImpl";

    @Override
    public void helloWorld(String text) {
        Log.e(TAG, "我是：" + text);
    }
}
