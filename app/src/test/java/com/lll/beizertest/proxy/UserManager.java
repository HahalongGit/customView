package com.lll.beizertest.proxy;

/**
 * Created by longlong on 2018/6/28.
 *
 * @ClassName: UserManager
 * @Description:
 * @Date 2018/6/28
 */

public interface UserManager {

    void addUser(String userId,String userName);

    void delUser(String userId);

    String findUser(String userId);

}
