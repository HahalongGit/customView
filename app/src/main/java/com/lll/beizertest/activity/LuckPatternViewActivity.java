package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;

/**
 * 九宫格解锁
 * 部分代码可以参考 https://github.com/sym900728/LockPattern
 */
public class LuckPatternViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_pattern_view);
    }
}
