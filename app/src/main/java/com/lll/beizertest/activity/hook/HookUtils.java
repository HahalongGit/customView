package com.lll.beizertest.activity.hook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by longlong on 2019/5/16.
 *
 * @ClassName: HookUtils
 * @Description: 参考 https://www.jianshu.com/p/3382cc765b39
 * @Date 2019/5/16
 */

public class HookUtils {

    private Context context;

    public void hookStartActivity(Activity context) {
        this.context = context;
    }

    /**
     * 动态代理
     */
    class StartActivityHandler implements InvocationHandler {

        private Object origin;

        public StartActivityHandler(Object origin) {
            this.origin = origin;
            try {
                // 这个版本的获取启动Activity方法 在ActivityManagerNative类中
                Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
                try {
                    Field iActivityManager = activityManagerNativeClazz.getDeclaredField("getDefault");
                    iActivityManager.setAccessible(true);
                    try {
                        Object singleTonObj = iActivityManager.get(null);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("startActivity".equals(method.getName())) {
                // startActivity(intent);
                Intent intent = null;
                int index = -1;
                for (int i = 0; i < args.length; i++) {
                    Object obj = args[i];
                    if (obj instanceof Intent) {
                        intent = (Intent) args[i];
                        index = i;
                    }
                }
                Intent newIntent = new Intent(context, HookActivityActivity.class);
                newIntent.putExtra("oldIntent", intent);
                args[index] = newIntent;

            }
            return method.invoke(origin, args);//调用startActivity
        }
    }

}
