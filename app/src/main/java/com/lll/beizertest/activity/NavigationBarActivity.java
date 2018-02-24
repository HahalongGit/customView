package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.view.toolbar.DefaultNavigationBar;

public class NavigationBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        //创建一个Navigation 添加到一个ViewGroup 中
        new DefaultNavigationBar.Builder(this)
                .setTitle("详情")
                .setRightText("更多")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NavigationBarActivity.this, "点击右边", Toast.LENGTH_SHORT).show();
                    }
                })
                .builder();
    }
}
