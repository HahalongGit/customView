package com.lll.beizertest.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Handler;

/**
 * Created by longlong on 2018/6/20.
 *
 * @ClassName: HookStartActivityUtil
 * @Description: 创建一个Hook 启动没有注册的Activity的工具
 * @Date 2018/6/20
 */

public class HookStartActivityUtil {

    private static final String TAG = "HookStartActivityUtil";

    private Context context;

    private Class<?> mProxyClass;
    /**
     * 参数Falg
     */
    private String EXTRA_ORIGIN_INTNET = "extra_origin_intent";

    public HookStartActivityUtil(Context context, Class<?> mProxyClass) {
        this.context = context.getApplicationContext();
        this.mProxyClass = mProxyClass;
    }

    public void hookStartActivity() throws Exception {

        Class<?> amnClass = Class.forName("android.app.IActivityManagerNative");
        Field gDefaultField = amnClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);
        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object iamInstance = mInstanceField.get(gDefault);//获取到ActivityManager的实现

        Class<?> iamClass = Class.forName("android.app.IActivityManager");
        iamInstance = Proxy.newProxyInstance(HookStartActivityUtil.class.getClassLoader(), new Class[]{iamClass}, new StartActivityInvicationHandler(iamInstance));
        mInstanceField.set(gDefault, iamInstance);//重新指定，设置成代理

    }

    public void hookLaunchActivity() throws Exception {
//        5.获取ActivityThread
        Class<?> atClass = Class.forName("android.app.ActivityThread");
        Field scatFidle = atClass.getDeclaredField("sCurrentActivityThread");
        scatFidle.setAccessible(true);
        Object sCurrentActivityThread = scatFidle.get(null);
//        6.获取ActivityThread中mH
        Field mHfield = atClass.getDeclaredField("mH");
        mHfield.setAccessible(true);
        Handler handler = (Handler) mHfield.get(sCurrentActivityThread);//这里需要熟悉handler源码。，知道handler代码执行顺序
//        7.hook handleLaunchActivity();
        //给Hanlder设置CallBack回调，通过反射设置
        Class<?> handlerClass = Class.forName("android.os.Handler");
        Field mCallbackField = handlerClass.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        mCallbackField.set(handler, new HandlerCallBack());
    }

    //handler 回调
    private class HandlerCallBack implements android.os.Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            //每一次发消息都会走这个方法，ActivityThread 中启动Activity的时候也会通过发消息来设置这些，我们拦截消息处理
            if (msg.what == 100) {//Launch_Activity 的常量就是100
                hanldeLaunchActivity(msg);
            }
            return false;
        }
    }

    private void hanldeLaunchActivity(Message msg) {
        Object record = msg.obj;
        //1.从record 获取过安检的Intent
        try {
            Field intentField = record.getClass().getDeclaredField("intent");
            Intent safeIntent = (Intent) intentField.get(record);
            //2.从safeIntent 中获取原来的Intent
            Intent originIntent = safeIntent.getParcelableExtra(EXTRA_ORIGIN_INTNET);
            if (originIntent != null) {
                //3.重新设置回去
                intentField.set(record, originIntent);//替换原来的Intent
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class StartActivityInvicationHandler implements InvocationHandler {

        private Object object;

        public StartActivityInvicationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e(TAG, "method:" + method.getName());
            //这里替换Intent 过AndoridManifest.xml 的检测
            if (method.getName().equals("startActivity")) {
                //获取原来的Intent
                Intent originIntent = (Intent) args[2];// IActivityManager 中的startActivity(...)方法中，第三个参数是 Intent
                //创建一个安全的Intent
                Intent safeIntent = new Intent(context, mProxyClass);
                args[2] = safeIntent;//替换成安全的
                safeIntent.putExtra(EXTRA_ORIGIN_INTNET, originIntent);//保存一个原来的Intent，检测完成后替换回来。
                //最后 hook 启动流程中的handleLaunchActivity方法，替换Intent
            }
            return method.invoke(object, args);
        }
    }

}
