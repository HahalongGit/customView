package com.lll.beizertest.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by longlong on 2018/6/29.
 *
 * @ClassName: LogHandler2
 * @Description:
 * @Date 2018/6/29
 */

public class LogHandler2 implements InvocationHandler {


    public  <T> T newProxyInstance(Class<T> clazz){
        //相当于 UserManager objectTarget.
        //this.clazz = clazz;
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("*******************Retrofit2方式实现动态代理******************");
        System.out.println("method-name:"+method.getName());
        Object result = method.invoke(this,args);
        return result;
    }
}
