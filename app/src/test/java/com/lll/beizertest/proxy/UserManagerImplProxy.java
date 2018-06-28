package com.lll.beizertest.proxy;

/**
 * Created by longlong on 2018/6/28.
 *
 * @ClassName: UserManagerImplProxy
 * @Description:
 * @Date 2018/6/28
 */

public class UserManagerImplProxy implements UserManager {

    private UserManager userManager;

    public UserManagerImplProxy(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void addUser(String userId, String userName) {
        try {
            System.out.println("UserManagerImplProxy.addUser 添加前做一些事情");
            userManager.addUser(userId,userName);
            System.out.println("UserManagerImplProxy.addUser 添加user后做一些事情");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delUser(String userId) {
        System.out.println("UserManagerImplProxy.delUser 删除用户");
        userManager.delUser(userId);
    }

    @Override
    public String findUser(String userId) {
        System.out.println("UserManagerImplProxy.findUser 查找用户");
        return userManager.findUser(userId);
    }
}
