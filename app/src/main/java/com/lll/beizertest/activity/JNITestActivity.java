package com.lll.beizertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lll.beizertest.R;
import com.lll.beizertest.jni.JNIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Jni 简单测试
 */
public class JNITestActivity extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_getJni)
    Button btnGetJni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jnitest);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_getJni})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getJni:
                tvContent.setText(new JNIUtils().StringFromJNI() + "----成功");
                break;
        }
    }
}
