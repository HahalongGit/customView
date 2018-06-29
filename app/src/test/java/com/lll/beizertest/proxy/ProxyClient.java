package com.lll.beizertest.proxy;

import com.lll.beizertest.api.ServiceApi;

import org.junit.Test;

/**
 * Created by longlong on 2018/6/28.
 *
 * @ClassName: ProxyClient
 * @Description:
 * @Date 2018/6/28
 */

public class ProxyClient {

    @Test
    public void Client1() {
        System.out.println("ProxyClient 测试");
        UserManager userManager = new UserManagerImpl();//实现类对象

        //代理创建，UserManagerImplProxy代理中处理一些其他的问题，添加日志等
        UserManager userManagerProxy = new UserManagerImplProxy(userManager);
        userManagerProxy.addUser("20180628", "龙龙");

        //代理客户端 不需要知道实现类是什么，只需要知道代理类UserManagerImplProxy即可
        //代理类和实现类实现了同样的接口，代理类通过委托类实现了相同的方法 ，导致大量代码重复
        //如果接口增加一个方法，实现类和代理类都要增加实现这新的方法
        //代理对象只服务于一种类型的对象（UserManagerImpl创建这个对象），如果要服务其他的对象，就需要创建多个实现类对每一种对象进行代理
        // 如果另外一个接口要实现代理，就需要再一次重写代理类和实现类。
        //即：静态代理只能为指定的接口服务，如果要为多个接口服务则需要创建多个代理类。
    }

    //引入动态代理
    //如上所示，静态代理每个代理只能为一个接口服务，所以我们就考虑有没有一种方式可以为所有的接口提供代理。这就是动态代理。
    //静态代理是在编译的时候就已经确定了对代理的对象，而动态代理是在运行的时候通过反射动态生成的代理对象。所以能够代理各种类型的对象。


    @Test
    public void Client2() {
        System.out.println("*************test2************");
        LogHandler logHandler = new LogHandler();
        UserManager userManager = (UserManager) logHandler.newProxyInstance(new UserManagerImpl());//代理UserManager
        userManager.addUser("20180628", "奔跑的地瓜");

        //动态代理不需要在写代理类，只有接口类和实现类
    }

    @Test
    public void Client3() {
        System.out.println("*************test3************");
        LogHandler logHandler = new LogHandler();
        // Retrofit2 创建对象的时候直接传入 Api接口
        //如代码：Retrofit2TestActivity 中查看 retrofit.create(ServiceApi.class);源码结果
        //示例：ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        // 为什么这里是错误的？Retrofit2 是怎么实现的
        UserManager userManager = (UserManager) logHandler.newProxyInstance(UserManager.class);
//        UserManager userManager = (UserManager) logHandler.newProxyInstance(UserManager.class);//代理UserManager
        userManager.addUser("20180628", "奔跑的地瓜");
    }

    @Test
    public void Client4() {
        System.out.println("*****************test4****************");
        LogHandler2 logHandler2 = new LogHandler2();
        UserManager userManager = logHandler2.newProxyInstance(UserManager.class);
        userManager.addUser("20180829", "奔跑的地瓜2");
    }

}
