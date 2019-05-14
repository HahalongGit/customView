package com.lll.beizertest.model;

import android.util.Log;

/**
 * Created by longlong on 2019/5/6.
 *
 * @ClassName: ArtsStudent
 * @Description:
 * @Date 2019/5/6
 */

public abstract class ArtsStudent extends Student {

    public static void function_static_mehod(){
        Log.e("TAG","function_static_mehod静态方法调用");
    }

    @Override
    protected abstract void setText();
}
