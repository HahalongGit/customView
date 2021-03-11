package com.lll.beizertest.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by longlong on 2018/4/4.
 *
 * @ClassName: MessageService
 * @Description: 进程间通讯Service
 * @Date 2018/4/4
 */

public class MessageService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        return userBinder;
        return null;
    }


    /**
     * 实现一个Binder （Aidl 会自动生成一个Stub java文件 包含两个子类，Stub 和 Proxy）
     */
//    private UserAidl.Stub userBinder = new UserAidl.Stub() {
//        @Override
//        public String getUserName() throws RemoteException {
//
//            return "龙龙";
//        }
//
//        @Override
//        public String getUserPassword() throws RemoteException {
//
//            return "myPassword";
//        }
//
//    };
}
