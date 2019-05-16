package com.lll.beizertest.activity.hook;

import android.app.Activity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by longlong on 2019/5/16.
 *
 * @ClassName: InstrumentationPryoxy
 * @Description:
 * @Date 2019/5/16
 */

public class InstrumentationPryoxy extends Instrumentation {

    private Instrumentation instrumentation;

    public InstrumentationPryoxy(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        try {
            // 获取方法
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            try {
                // 调用方法
                ActivityResult result = (ActivityResult) execStartActivity.invoke(instrumentation, who, contextThread,
                        token, target, intent, requestCode, options);
                return result;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
