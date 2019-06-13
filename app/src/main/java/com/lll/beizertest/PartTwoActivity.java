package com.lll.beizertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.activity.provider.ProviderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第二部分测试
 */
public class PartTwoActivity extends AppCompatActivity {

    @BindView(R.id.btn_provider)
    Button btnProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_two);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_provider})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_provider: {
                Intent intent = new Intent(this, ProviderActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
