package com.lll.beizertest.view.bannerViewPager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by longlong on 2018/1/19.
 *
 * @ClassName: BannerScroller
 * @Description:
 * @Date 2018/1/19
 */

public class BannerScroller extends Scroller {

    private int mScrollDuration = 800;

    /**
     * 设置滑动时间
     * @param scrollDuration
     */
    public void setScrollerDuration(int scrollDuration) {
        this.mScrollDuration = mScrollDuration;
    }

    public BannerScroller(Context context) {
        this(context,null);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        this(context, interpolator,false);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);

    }
}
