package com.lll.beizertest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lll.beizertest.receiver.MyReceiver;
import com.lll.beizertest.receiver.MyReceiver1;
import com.lll.beizertest.receiver.MyReceiver2;
import com.lll.beizertest.router.RouterPathConstants;
import com.lll.beizertest.test.MiddleStudent;
import com.lll.beizertest.test.Student;

import org.litepal.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 页面功能导航
 */

@Route(path = RouterPathConstants.ACTIVITY_URL_GUIDE)
public class GuideActivity extends AppCompatActivity {

    private static final String TAG = "GuideActivity";

    @BindView(R.id.btn_partOne)
    Button btnPartOne;
    @BindView(R.id.btn_partTwo)
    Button btnPartTwo;

    private InnerReceiver mInnerReceiver;

    private MyReceiver mMyReceiver;

    private MyReceiver1 mMyReceiver1;

    private MyReceiver2 mMyReceiver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        ClassLoader classLoader = getClassLoader();
        while (classLoader != null) {
            Log.e(TAG, "classLoader-:" + classLoader.getClass().getName());
            classLoader = classLoader.getParent();
        }

        mMyReceiver = new MyReceiver();
        mMyReceiver1 = new MyReceiver1();
        mMyReceiver2 = new MyReceiver2();
        IntentFilter intentFilter = new IntentFilter();
        IntentFilter intentFilter1 = new IntentFilter();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter.addAction("com.lll.beizertest.receiver");
        intentFilter.setPriority(1000);
        intentFilter1.addAction("com.lll.beizertest.receiver");
        intentFilter1.setPriority(0);
        intentFilter2.addAction("com.lll.beizertest.receiver");
        intentFilter2.setPriority(-100);
        registerReceiver(mMyReceiver, intentFilter);
        registerReceiver(mMyReceiver1, intentFilter1);
        registerReceiver(mMyReceiver2, intentFilter2);

////        动态注册的广播没问题
//        mInnerReceiver = new InnerReceiver();
//
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.lll.InnerReceiver");
//        registerReceiver(mInnerReceiver, filter);

    }

    @OnClick({R.id.btn_partOne, R.id.btn_partTwo, R.id.btn_partThree, R.id.btn_sendBroadcast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_partOne: {
                ARouter.getInstance().build(RouterPathConstants.ACTIVITY_URL_PART_ONE).navigation();
                break;
            }
            case R.id.btn_partTwo: {
                ARouter.getInstance().build(RouterPathConstants.ACTIVITY_URL_PART_TWO).navigation();
                break;
            }
            case R.id.btn_partThree: {
                ARouter.getInstance().build(RouterPathConstants.ACTIVITY_URL_PART_THREE).navigation();
                break;
            }
            case R.id.btn_sendBroadcast: {
                Intent intent = new Intent("com.lll.beizertest.receiver");
//                sendBroadcast(intent);
                sendOrderedBroadcast(intent, null, null, null, 1, "这是有序广播初始数据", null);
//                startService();
//                bindService()
//                registerReceiver()
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mInnerReceiver);

        unregisterReceiver(mMyReceiver);
        unregisterReceiver(mMyReceiver1);
        unregisterReceiver(mMyReceiver2);
    }

    public class InnerReceiver extends BroadcastReceiver {

        public InnerReceiver() {
        }

        int i = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "InnerReceiver执行了--" + i++);
        }
    }

}
