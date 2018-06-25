package com.lll.beizertest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * handler 源码分析,和原理梳理
 */
public class HandlerActivity extends AppCompatActivity {

    @BindView(R.id.tv_handlerText)
    TextView tvHandlerText;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvHandlerText.setText("handler 修改了数据后");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Message message = mHandler.obtainMessage();
                    message.what = 1;//设置参数
                    mHandler.sendMessage(message);//第一次发送没有延迟的消息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Looper.prepare();
                Looper.loop();
                mHandler.sendEmptyMessageDelayed(1, 1000);//第二次发送消息
                mHandler.sendEmptyMessageDelayed(1, 500);//第三次发送消息
                tvHandlerText.setText("");
            }
        }).start();
    }


}
