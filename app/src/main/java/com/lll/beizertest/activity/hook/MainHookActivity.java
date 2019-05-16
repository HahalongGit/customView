package com.lll.beizertest.activity.hook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Hoook技术练习
 */
public class MainHookActivity extends AppCompatActivity {

    @BindView(R.id.btn_hooktest1)
    Button btnMainHook;
    @BindView(R.id.btn_hookActivity)
    Button btnHookActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hook);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_hooktest1, R.id.btn_hookActivity})
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
        }
    }
}
