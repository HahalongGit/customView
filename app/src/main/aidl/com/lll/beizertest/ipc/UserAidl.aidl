// UserAidl.aidl
package com.lll.beizertest.ipc;

// Declare any non-default types here with import statements

//进程间通讯的协议
interface UserAidl {

   String getUserName();

   String getUserPassword();

//   void addUser(User userInfo);
//   User getUserInfo(String id);

}
