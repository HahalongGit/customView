package com.lll.beizertest.activity;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_pager);
        customViewPager = findViewById(R.id.customViewPager);
//        customViewPager.setPageMargin(20);
//        customViewPager.setOffscreenPageLimit(3);
        customViewPager.setPageTransformer(false,new ScaleTransformer(this));
        adapter = new CustomViewPagerAdapter(this);
        //customViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        customViewPager.setAdapter(adapter);
    }
}
