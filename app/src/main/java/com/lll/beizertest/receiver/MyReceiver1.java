package com.lll.beizertest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * TODO:describe what the class or interface does.
 *
 * @author RunningDigua
 * @date 2021/1/7
 */
public class MyReceiver1 extends BroadcastReceiver {

    public MyReceiver1() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = getResultData();
        Log.e("MyReceiver", "MyReceiver1：" + message);
        Toast.makeText(context, "MyReceiver1"+message, Toast.LENGTH_SHORT).show();
        //终止广播
        abortBroadcast();
    }
}
