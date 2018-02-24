package com.lll.beizertest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.adapter.CustomViewPagerAdapter;
import com.lll.beizertest.transformer.ScaleTransformer;
import com.lll.beizertest.view.bannerViewPager.BannerAdapter;
import com.lll.beizertest.view.bannerViewPager.BannerViewPager;
import com.lll.beizertest.view.customviewpager.BannerViewAdapter;
import com.lll.beizertest.view.customviewpager.BannerViewpager;
import com.lll.beizertest.view.customviewpager.MyBannerAdapter;

/**
 * ViewpPager PagerTransformer 的使用
 */
public class CustomViewPagerActivity extends AppCompatActivity {

   // private ViewPager customViewPager;

    private BannerViewpager bannerViewPager;

    private CustomViewPagerAdapter adapter;

    private int[] imgRes = {R.mipmap.bg_blue, R.mipmap.bg_orange, R.mipmap.bg_pink, R.mipmap.bg_puple};

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandler.sendEmptyMessageDelayed(1000,2000);
           // customViewPager.setCurrentItem(customViewPager.getCurrentItem()+1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_pager);
//        customViewPager = findViewById(R.id.customViewPager);
//
        bannerViewPager = findViewById(R.id.bannerViewPager);
////        customViewPager.setPageMargin(20);
////        customViewPager.setOffscreenPageLimit(3);
//        customViewPager.setPageTransformer(false, new ScaleTransformer(this));
        adapter = new CustomViewPagerAdapter(this);
//        //customViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
//        customViewPager.setAdapter(adapter);
//        mHandler.sendEmptyMessageDelayed(1000,1000);
//        customViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                customViewPager.setCurrentItem(position,true);
//            }
//
//        });



        bannerViewPager.setAdapter(new BannerViewAdapter() {
            @Override
            public View getView(int position, ViewGroup convertView) {
                View view1 = LayoutInflater.from(CustomViewPagerActivity.this).inflate(R.layout.list_transformer_adapter_layout, convertView, false);
                ImageView imageView = view1.findViewById(R.id.imageView);
                imageView.setImageResource(imgRes[position]);
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CustomViewPagerActivity.this, "点击", Toast.LENGTH_SHORT).show();
                    }
                });
                return view1;
            }

            @Override
            public int getCount() {
                return imgRes.length;
            }
        });
        bannerViewPager.setPageTransformer(false, new ScaleTransformer(this));
       // bannerViewPager.setAdapter(adapter);
        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bannerViewPager.setCurrentItem(position,true);
            }
        });
        bannerViewPager.startScroll();
    }
}
