package com.lll.beizertest;

import android.app.Application;
import android.os.Debug;

import com.lll.beizertest.activity.ProxyActivity;
import com.lll.beizertest.utils.HookStartActivityUtil;
import com.tencent.bugly.crashreport.CrashReport;

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
        Debug.startMethodTracing("longlongApp");
        CrashReport.initCrashReport(getApplicationContext(), "e903b8d2b5", false);
        try {
//            Thread.sleep(2000);//测试耗时加载对应用启动的影响，白屏时间增加
            //对 插件化处理
            HookStartActivityUtil hookStartActivityUtil = new HookStartActivityUtil(this, ProxyActivity.class);
            hookStartActivityUtil.hookStartActivity();
            hookStartActivityUtil.hookLaunchActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Debug.stopMethodTracing();
    }
}
