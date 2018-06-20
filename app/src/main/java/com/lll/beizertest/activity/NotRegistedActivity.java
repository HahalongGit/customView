package com.lll.beizertest.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;

/**
 * 测试插件化开发中 绕过AndroidManifest.xml 检测 这个Activity 没有添加注册信息
 */
public class NotRegistedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_registed);
    }
}
