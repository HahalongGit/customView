package com.lll.beizertest;

import android.app.Application;
import android.os.Debug;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
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


public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
//        Trace.beginSection("longlongTrace");
        Debug.startMethodTracing("longlongAppTrace");

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
//        LeakCanary.install(this);
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
//        Trace.endSection();
//        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//        Log.d("TAG", "Max memory is " + maxMemory + "KB");//Max memory is 262144KB ≈ 256M
    }
}
