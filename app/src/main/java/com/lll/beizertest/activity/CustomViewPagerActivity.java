package com.lll.beizertest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lll.beizertest.R;
import com.lll.beizertest.adapter.CustomViewPagerAdapter;
import com.lll.beizertest.transformer.ScaleTransformer;

/**
 * ViewpPager PagerTransformer 的使用
 */
public class CustomViewPagerActivity extends AppCompatActivity {

    private ViewPager customViewPager;

    private CustomViewPagerAdapter adapter;

    private int count;

    private int currentPage;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandler.sendEmptyMessageDelayed(1000,2000);
            if(currentPage<count){
                currentPage++;
                customViewPager.setCurrentItem(currentPage,true);
            }else {
                currentPage = 0;
                customViewPager.setCurrentItem(currentPage,true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_pager);
        customViewPager = findViewById(R.id.customViewPager);
//        customViewPager.setPageMargin(20);
//        customViewPager.setOffscreenPageLimit(3);
        customViewPager.setPageTransformer(false, new ScaleTransformer(this));
        adapter = new CustomViewPagerAdapter(this);
        //customViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        customViewPager.setAdapter(adapter);
        count = adapter.getCount();
        mHandler.sendEmptyMessageDelayed(1000,1000);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                customViewPager.setCurrentItem(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
