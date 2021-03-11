package com.lll.beizertest.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * IPC 学习相关
 */
public class IPCActivity extends AppCompatActivity {

    private static final String TAG = "IPCActivity";

    @BindView(R.id.btn_username)
    Button btnUsername;
    @BindView(R.id.btn_password)
    Button btnPassword;
    private ServiceConnection serviceConnection;

//    private UserAidl userAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        ButterKnife.bind(this);
        //创建一个链接
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(TAG, "链接好了");
                // Service 是服务端给我们的Binder
//                userAidl = UserAidl.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e(TAG, "断开链接");
            }
        };


        //为了方便 同一个应用的activity写
        // startService()的底层源码中调用了IBiner 让服务和客户产生关联
//        private static final Singleton<IActivityManager> IActivityManagerSingleton =
//                new Singleton<IActivityManager>() {
//                    @Override
//                    protected IActivityManager create() {
//                        final IBinder b = ServiceManager.getService(Context.ACTIVITY_SERVICE);
//                        final IActivityManager am = IActivityManager.Stub.asInterface(b);
//                        return am;
//                    }
//                };
        startService(new Intent(this, MessageService.class));

        Intent intent = new Intent(this, MessageService.class);
        intent.setAction("com.lll.beizertest.ipc.user");
        //隐示意图 绑定服务
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick({R.id.btn_username, R.id.btn_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_username:
//                try {
//                    //输出结果：
////                    Log.e(TAG,"username:"+userAidl.getUserName());
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.btn_password:
//                try {
////                    Log.e(TAG,"password:"+userAidl.getUserPassword());
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }
}
