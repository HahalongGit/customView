package com.lll.beizertest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 有序广播接收器
 *
 * @author RunningDigua
 * @date 2021/1/7
 */
public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Log.e("MyReceiver", "MyReceiver：" + resultData);
        setResultData("MyReceiver中修改数据~");
    }

}
