package com.lll.beizertest.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lll.beizertest.R;
import com.lll.beizertest.fragment.ItemFragment;
import com.lll.beizertest.view.ColorTrackTextView;

import java.util.ArrayList;
import java.util.List;

public class ColorTrackTextActivity extends AppCompatActivity implements View.OnClickListener {

    private ColorTrackTextView colorTrackTextView;

    private ViewPager viewPager;

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout mIndicatorContainer;// 变成通用的
    private List<ColorTrackTextView> mIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_track_text);
        findViewById(R.id.btn_leftToRight).setOnClickListener(this);
        findViewById(R.id.btn_rightToLeft).setOnClickListener(this);
        colorTrackTextView = findViewById(R.id.colorTrackTextView);
        mIndicatorContainer = findViewById(R.id.ll_title);
        viewPager = findViewById(R.id.viewPager);
        mIndicators = new ArrayList<>();
        initIndicator();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG","position -> "+position +"  positionOffset -> "+positionOffset);
                // position 代表当前的位置
                // positionOffset 代表滚动的 0 - 1 百分比
                // 1.左边  位置 position
                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1-positionOffset);

                try {
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                }catch (Exception e){

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            // 设置颜色
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(colorTrackTextView);
            // 加入集合
            mIndicators.add(colorTrackTextView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_leftToRight:{
                Log.e("TAG","btn_leftToRight");
                colorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                ValueAnimator animator = ObjectAnimator.ofFloat(0, 1);
                animator.setDuration(3*1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        colorTrackTextView.setCurrentProgress(value);
                    }
                });
                animator.start();
                break;
            }
            case R.id.btn_rightToLeft:{
                Log.e("TAG","btn_rightToLeft");
                colorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                ValueAnimator animator = ObjectAnimator.ofFloat(0, 1);
                animator.setDuration(3*1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        colorTrackTextView.setCurrentProgress(value);
                    }
                });
                animator.start();
                break;
            }
        }
    }
}
