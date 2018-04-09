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
 * @ClassName: QQMessageService
 * @Description:  比如：一个默认的 QQ 聊天通讯中 Service   代码需要轻
 * @Date 2018/4/9
 */

public class QQMessageService extends Service {

    private static final String TAG = "QQMessageService";

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Log.e(TAG,"进程onCreate");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand1");
        startForeground(1,new Notification());//开启一个前台进程，优先级最高。这里设置一个Notification 通知。
        bindService(new Intent(this,GuardService.class),mServiceConnection, Context.BIND_IMPORTANT);
        //返回 START_STICKY 对于 服务的启动有好处
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {};
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"onServiceConnected");
            Toast.makeText(QQMessageService.this, "QQMessageService建立连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"onServiceConnected");
            Toast.makeText(QQMessageService.this, "QQMessageService断开链接", Toast.LENGTH_SHORT).show();
            //重新启动 守护进程
            startService(new Intent(QQMessageService.this,GuardService.class));
            //重新绑定守护进程
            bindService(new Intent(QQMessageService.this,GuardService.class),mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

}
