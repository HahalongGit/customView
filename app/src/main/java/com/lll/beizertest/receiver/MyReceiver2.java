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
public class MyReceiver2 extends BroadcastReceiver {

    public MyReceiver2() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = getResultData();
        Log.e("MyReceiver", "MyReceiver2：" + message);
        Toast.makeText(context, "MyReceiver2" + message, Toast.LENGTH_SHORT).show();
    }

}
