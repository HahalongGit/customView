package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lll.beizertest.R;
import com.lll.beizertest.view.LoveLayout;

public class LivePerformActivity extends AppCompatActivity implements View.OnClickListener{

    private LoveLayout loveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_perform);
        loveLayout = findViewById(R.id.loveLayout);
        findViewById(R.id.btn_addLove).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addLove:{
                loveLayout.addLove();
                break;
            }
        }
    }
}
