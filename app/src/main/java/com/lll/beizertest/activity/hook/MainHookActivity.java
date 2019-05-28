package com.lll.beizertest.activity.hook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lll.beizertest.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Hoook技术练习
 */
public class MainHookActivity extends AppCompatActivity {

    private static final String TAG = "MainHookActivity";

    @BindView(R.id.btn_hooktest1)
    Button btnMainHook;
    @BindView(R.id.btn_hookActivity)
    Button btnHookActivity;
    @BindView(R.id.btn_setUI)
    Button btnSetUI;

    TextView tvDefaultText;
    private Socket socket;

    private ServerSocket serverSocket;


    private StringBuilder stringBuilder;

    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hook);
        ButterKnife.bind(this);
        tvDefaultText = findViewById(R.id.tv_defaultText);
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
//        Iterator<String> iterator = list.iterator();
//        if(iterator.hasNext()){
//            String s = iterator.next();
//            if(s.equals("C")){
//                Log.e(TAG,"ssss--"+s);
//                iterator.remove();
//            }
//        }
//        for (String s : list) {
//            if(s.equals("C")){
//                list.add("new Char");
//            }
//        }


    }

    private void setUi() {
        Log.e(TAG, "ThreadName:" + Thread.currentThread().getName() + "-id-" + Thread.currentThread().getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "ThreadName:" + Thread.currentThread().getName() + "-id-" + Thread.currentThread().getId());
                tvDefaultText.setText("ThreadName:" + Thread.currentThread().getName() + "设置Text");
            }
        }).start();
    }


    @OnClick({R.id.btn_hooktest1, R.id.btn_hookActivity,R.id.btn_setUI})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_hooktest1: {
                Intent intent = new Intent(this, HookListenerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_hookActivity: {
                Intent intent = new Intent(this, HookActivityActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_setUI: {
                setUi();
                break;
            }
        }
    }
}
