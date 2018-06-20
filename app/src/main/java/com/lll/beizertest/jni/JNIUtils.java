package com.lll.beizertest.jni;

/**
 * Created by longlong on 2018/6/11.
 *
 * @ClassName: JNIUtils
 * @Description:
 * @Date 2018/6/11
 */

public class JNIUtils {

    static {
        System.loadLibrary("native-lib");//native-lib
    }

    public native String StringFromJNI();

}
