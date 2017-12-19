package com.lll.beizertest.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lll.beizertest.R;
import com.lll.beizertest.view.MyScrollView;

/**
 * 状态栏仿QQ动态效果，设置状态栏透明，然后根据滑动的距离监听设置ToolBar的透明度
 */
public class StatusBarActivity extends AppCompatActivity {

    private MyScrollView scrollView;

    private Toolbar toolBarStartBar;

    private TextView tvTitile;

    private ImageView ivHeadImage;

    private int imageHeight;

    private int toolBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            //5.0以上设置全屏
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            // TODO: 2017/12/19 小于5.0 的需要添加一饿View设置
        }
        toolBarStartBar = findViewById(R.id.toolBarStartBar);
        scrollView = findViewById(R.id.scrollView);
        tvTitile = findViewById(R.id.tv_titile);
        ivHeadImage = findViewById(R.id.iv_headImage);
        setSupportActionBar(toolBarStartBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //拿不到高度，onCreate中没有完成测量
        //imageHeight = ivHeadImage.getMeasuredHeight();
        ivHeadImage.post(new Runnable() {
            @Override
            public void run() {
                //post 方法加入一个队列，在测量后执行
                imageHeight = ivHeadImage.getMeasuredHeight();
                toolBarHeight = toolBarStartBar.getMeasuredHeight();
            }
        });
        scrollView.setOnScrollChangedListener(new MyScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if(imageHeight!=0){
                    float alpha = (float) t/(imageHeight-toolBarHeight);
                    if(alpha>1){
                        alpha = 1;
                    }
                    if(alpha<0){
                        alpha = 0;
                    }
                    toolBarStartBar.setAlpha(alpha);
                }
            }
        });

    }
}
