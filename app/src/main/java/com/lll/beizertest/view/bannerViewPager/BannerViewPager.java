package com.lll.beizertest.view.bannerViewPager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by longlong on 2018/1/19.
 *
 * @ClassName: BannerViewPager
 * @Description:
 * @Date 2018/1/19
 */

public class BannerViewPager extends ViewPager {

    private BannerAdapter mBannerAdpter;

    /**
     * 发送消息的message what
     */
    private final int SCROLL_MESSAGE = 0X0011;

    /**
     * 页面切换间隔时间
     */
    private int myCutDownTime = 1000;

    /**
     * 自定义的改变页面的Scroller类
     */
    private BannerScroller mScroller;

    /**
     * 是否按下
     */
    private boolean isPressed;

    private View convertView;

    /**
     * 复用的View 集合
     */
    private List<View> converViews;

    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(!isPressed){
                setCurrentItem(getCurrentItem() + 1);
                startRoll();
            }
        }
    };


    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        //改变ViewPager 切换的时间 源码调用了mScroller 的方法，这还是一个private的方法
        //自定义 点指示器和文字描述

        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            //当前属性在那个类，第二个参数表示参数是什么值
            mScroller = new BannerScroller(context);
            scroller.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        converViews = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                isPressed = true;
                break;
            }
            case MotionEvent.ACTION_UP:{
                isPressed = false;
                startRoll();
                break;
            }
        }
        return super.onTouchEvent(ev);
    }


    @Override
    public void setOffscreenPageLimit(int limit) {
        super.setOffscreenPageLimit(limit);
    }

    /**
     * 设置页面切换时间
     *
     * @param scrollerDuration
     */
    public void setBannerScrolleTime(int scrollerDuration) {
        mScroller.setScrollerDuration(scrollerDuration);
    }

    /**
     * 设置自定义BannerAdapter
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mBannerAdpter = adapter;
        //设置父类ViewPager的adapter
        super.setAdapter(new BannerPagerAdapter());
    }

    /**
     * 开始自动滚动
     */
    public void startRoll() {
        mHander.removeMessages(SCROLL_MESSAGE);//防止调用多次startRoll
        mHander.sendEmptyMessageDelayed(SCROLL_MESSAGE, myCutDownTime);
    }


    /**
     * 解决内存泄露的问题
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHander.removeMessages(SCROLL_MESSAGE);
        mHander = null;
    }

    public class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View bannerView = mBannerAdpter.getView(position,getConvertView());
            //position/mBannerAdpter.getCount() 计算实际位置，设置这个导致Pager切换变慢
            container.addView(bannerView);
            return bannerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
//            object = null;
            converViews.add((View) object);//模拟ListView 把移除的View 添加复用View
        }
    }

    /**
     * 获取一个复用的View
     * @return
     */
    private View getConvertView() {
        for (int i = 0; i < converViews.size(); i++) {
            if(converViews.get(i).getParent()==null){//复用的时候判断是不是已经从父布局移除了
                return converViews.get(i);
            }
        }
        return null;
    }

}
