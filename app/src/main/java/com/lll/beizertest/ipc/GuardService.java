package com.lll.beizertest.ipc;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.lll.beizertest.ProcessConnection;

/**
 * Created by longlong on 2018/4/9.
 *
 * @ClassName: GuardService
 * @Description: 设置一个守护进程。 双进程通信
 * @Date 2018/4/9
 */

public class GuardService extends Service {

    private static final String TAG = "GuardService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1,new Notification());
        Log.e(TAG,"onStartCommand1");
        //绑定Service 建立连接
        bindService(new Intent(this,QQMessageService.class),mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private ProcessConnection.Stub stub = new ProcessConnection.Stub() {
        // aidl 没写方法 空实现
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"onServiceConnected");
            Toast.makeText(GuardService.this, "GuardService建立连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开的时候重新启动另一个QQMessageService，这个QQMessageService 就是我们的主要的Service
            startService(new Intent(GuardService.this,QQMessageService.class));
            //重新绑定
            bindService(new Intent(GuardService.this,QQMessageService.class),mServiceConnection, Context.BIND_IMPORTANT);
            Toast.makeText(GuardService.this, "GuardService断开连接", Toast.LENGTH_SHORT).show();
        }
    };

}
