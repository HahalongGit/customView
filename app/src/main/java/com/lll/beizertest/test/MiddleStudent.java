package com.lll.beizertest.test;

import android.util.Log;

/**
 * Created by longlong on 2019/6/29.
 *
 * @ClassName: MiddleStudent
 * @Description:
 * @Date 2019/6/29
 */

public class MiddleStudent extends Student {

    private static final String TAG = "MiddleStudent";


    @Override
    public void haveClass() {
        Log.e(TAG,"MiddleStudent - have class 1");
        super.haveClass();
        Log.e(TAG,"MiddleStudent - have class 2");
    }
}
