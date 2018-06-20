package com.lll.beizertest;

import android.app.Application;

import com.lll.beizertest.activity.ProxyActivity;
import com.lll.beizertest.utils.HookStartActivityUtil;

/**
 * Created by longlong on 2018/4/4.
 *
 * @ClassName: MyApplication
 * @Description:
 * @Date 2018/4/4
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            //对 插件化处理
            HookStartActivityUtil hookStartActivityUtil = new HookStartActivityUtil(this, ProxyActivity.class);
            hookStartActivityUtil.hookStartActivity();
            hookStartActivityUtil.hookLaunchActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
