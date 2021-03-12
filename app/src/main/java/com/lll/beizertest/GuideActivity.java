package com.lll.beizertest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lll.beizertest.databinding.ActivityGuideBinding;
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

    private ActivityGuideBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        mBinding = ActivityGuideBinding.inflate(getLayoutInflater());

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
                ARouter.getInstance()
                        .build(RouterPathConstants.ACTIVITY_URL_PART_ONE)
                        .withString(PartOneActivity.PART_ONT_PARAM_FLAG, "我是通过ARouter传递的数据~")
                        .withInt("age", 22)
                        .navigation();
                break;
            }
            case R.id.btn_partTwo: {
                ARouter.getInstance().build(RouterPathConstants.ACTIVITY_URL_PART_TWO).navigation();
                break;
            }
            case R.id.btn_partThree: {
                ARouter.getInstance().build(RouterPathConstants.ACTIVITY_URL_PART_THREE).navigation(this, 200);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult-data:" + data.getStringExtra("Data"));
        }
    }

    /**
     * TRIM_MEMORY_UI_HIDDEN 回调只有当程序中的所有UI组件全部不可见的时候才会触发，这和onStop方法还是有河大的区别的，
     * 因为onStop方法只是当一个Activity完全不可见的时候就会调用，比如说用户单开了我程序的另外一个Activity，因此，我们
     * 可以在onStop方法中去释放一些Activity相关的资源，比如说取消网络连接或者注销广播接收器等。当时像UI相关的资源应该
     * 一直要等到onTrimMemory的TRIM_MEMORY_UI_HIDDEN之后才释放，这样可以保证用户是从我们的程序的一个Activity回到了
     * 另外一个Activity，界面的相关资源都不需要重新加载，从而提高响应速度。
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            //释放内存，释放不用的对象内存
            case TRIM_MEMORY_UI_HIDDEN: {
                Log.e(TAG, "onTrimMemory-ui-hidden");
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

    public static class InnerReceiver extends BroadcastReceiver {

        public InnerReceiver() {
        }

        int i = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "InnerReceiver执行了--" + i++);
        }
    }

}
