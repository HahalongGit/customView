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
public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BootBroadcastReceiver";

    public BootBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "BootBroadcastReceiver-开机了" + intent.getAction());
        Toast.makeText(context, "开机广播", Toast.LENGTH_SHORT).show();

    }

}
