package com.lll.beizertest.proxy;

/**
 * Created by longlong on 2018/6/28.
 *
 * @ClassName: UserManagerImpl
 * @Description: UserManager 代理对象的实现类
 * @Date 2018/6/28
 */

public class UserManagerImpl implements UserManager {

    @Override
    public void addUser(String userId, String userName) {
        System.out.println("UserManagerImpl.addUser方法执行..");
    }

    @Override
    public void delUser(String userId) {
        System.out.println("UserManagerImpl.delUser方法执行..");
    }

    @Override
    public String findUser(String userId) {
        System.out.println("UserManagerImpl.findUser方法执行..");
        return "find到的用户："+userId;
    }
}
