package com.lll.beizertest.view.customviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longlong on 2018/2/6.
 *
 * @ClassName: BannerViewpager
 * @Description:
 * @Date 2018/2/6
 */

public class BannerViewpager extends ViewPager {

    private final int  SCROLL_MESSAGE = 1000;

    private boolean isOnTouch = false;

    private BannerViewAdapter mBannerAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("TAG","handleMessage-isOnTouch:"+isOnTouch);
            if(!isOnTouch){
                Log.e("TAG","getCurrentItem:"+getCurrentItem());
                setCurrentItem(getCurrentItem()+1);
                startScroll();
            }
        }
    };

    public BannerViewpager(Context context) {
        this(context,null);
    }

    public BannerViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:{
                isOnTouch = true;
                Log.e("TAG","isOnTouch-ACTION_DOWN:"+isOnTouch);
                break;
            }
            case MotionEvent.ACTION_UP:{
                isOnTouch = false;
                Log.e("TAG","isOnTouch-ACTION_UP:"+isOnTouch);
                startScroll();
                break;
            }
        }
        return super.onTouchEvent(ev);
    }


//    public void setAdapter(BannerViewAdapter bannerAdapter ){
//        this.mBannerAdapter = bannerAdapter;
//       // super.setAdapter(new BannerAdapter());
//    }

    public void startScroll(){
        mHandler.removeMessages(SCROLL_MESSAGE);
        Log.e("TAG","isOnTouch-startScroll");
        mHandler.sendEmptyMessageDelayed(SCROLL_MESSAGE, 1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //mHandler.removeMessages(SCROLL_MESSAGE);
    }

    public class BannerAdapter extends PagerAdapter{

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
            View view  = mBannerAdapter.getView(position%mBannerAdapter.getCount(),container);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

}
