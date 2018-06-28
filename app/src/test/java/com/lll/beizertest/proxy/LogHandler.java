package com.lll.beizertest.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by longlong on 2018/6/28.
 *
 * @ClassName: LogHandler
 * @Description: 动态代理的 InvocationHandler 实现
 * @Date 2018/6/28
 */

public class LogHandler implements InvocationHandler {

    //Object 对象 代理任意对象
    private Object objectTarget;


    public Object newProxyInstance(Object objectTarget){
        //相当于 UserManager objectTarget.
        this.objectTarget = objectTarget;
        return Proxy.newProxyInstance(objectTarget.getClass().getClassLoader(),objectTarget.getClass().getInterfaces(),this);
    }

    /**
     *
     * @param proxy 代理对象
     * @param method 被调用方法
     * @param args 方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("****************动态代理实现invoke***************");
        System.out.println("method-name:"+method.getName());
        //method.invoke(objectTarget,args) 相当于调用 被代理对象的  addUser(userId,userName) 方法
        Object result = method.invoke(objectTarget,args);
        return result;
    }
}
