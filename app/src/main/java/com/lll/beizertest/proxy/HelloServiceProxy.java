package com.lll.beizertest.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO:describe what the class or interface does.
 *
 * @author RunningDigua
 * @date 2020/10/14
 */
public class HelloServiceProxy implements InvocationHandler {

    private static final String TAG = "HelloServiceProxy";

    private Object target;

    public HelloServiceProxy(Object target) {
        this.target = target;
    }

//    public Object bind(Object target,Class[]interfaces){
//        this.target = target;
//        return Proxy.newProxyInstance(target.getClass().getClassLoader(),interfaces,this);
//    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e(TAG,"###################动态代理##################");
        Object result = null;
        Log.e(TAG,"准备调用hello" );
        Log.e(TAG,"准备调用proxy--" +proxy.getClass().getName());//这个是代理对象$Proxy0
        Log.e(TAG,"准备调用的方法："+method.getName() );
        Log.e(TAG,"Method："+method );
        result = method.invoke(target,args);//
        Log.e(TAG, "invoke-"+"调用hello结束-result:"+result );
        Log.e(TAG, "invoke-"+"调用hello结束" );
        return result;
    }
}
