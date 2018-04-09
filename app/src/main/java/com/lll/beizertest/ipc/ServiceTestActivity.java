package com.lll.beizertest.ipc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceTestActivity extends AppCompatActivity {

    @BindView(R.id.btn_serviceTest)
    Button btnServiceTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_serviceTest)
    public void onViewClicked() {
        Log.e("TAG","onViewClicked进程测试");
        startService(new Intent(this,QQMessageService.class));
        startService(new Intent(this,GuardService.class));
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            //5.0以上可以
            startService(new Intent(this,JobWakeupService.class));
        }
    }
}
