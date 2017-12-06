package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;
import com.lll.beizertest.view.MessageBubbleView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MessageBubbleView.attachView(findViewById(R.id.iv_showImage));
    }

}
