package com.lll.beizertest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/12/11.
 *
 * @ClassName: DrawLayout
 * @Description:
 * @Date 2017/12/11
 */

/**
 * 侧滑实现
 */
public class DrawLayout extends HorizontalScrollView {

    private int mMenuWidth = 65;
    //private DrawerLayout drawerLayout;

    private View mMenuView;

    private View mContentView;

    public DrawLayout(Context context) {
        this(context, null);
    }

    public DrawLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawLayout);
        float rightMargin = a.getDimension(R.styleable.DrawLayout_menuWidth, 50);
        mMenuWidth = (int) (getResources().getDisplayMetrics().widthPixels - dp2px(rightMargin));
        a.recycle();
    }


    /**
     * 布局加载完毕再回回调的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //指定content的宽度是内容的宽度
        //制定menu的宽度是muenu减去属性的宽度
        ViewGroup viewGoup = (ViewGroup) getChildAt(0);
        int childCount = viewGoup.getChildCount();
        if (childCount != 2) {
            new RuntimeException("只能包含两个子View！");
        }
        mMenuView = viewGoup.getChildAt(0);
        mContentView = viewGoup.getChildAt(1);
        ViewGroup.LayoutParams menuParam = mMenuView.getLayoutParams();
        menuParam.width = mMenuWidth;
        mMenuView.setLayoutParams(menuParam);
        ViewGroup.LayoutParams contentParam = mContentView.getLayoutParams();
        contentParam.width = getResources().getDisplayMetrics().widthPixels;
        mContentView.setLayoutParams(contentParam);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // getScrollX(); 滑动的距离和 onScrollChanged l 的距离一样
        Log.e("TAG", "onTouchEvent:" + getScrollX());
        if (MotionEvent.ACTION_UP == ev.getAction()) {
            if (getScrollX() < mMenuWidth / 2f) {
                openMenu();
            } else {
                closeMenu();
            }
            return false;//？
        }
        return super.onTouchEvent(ev);
    }

    private void openMenu() {
        smoothScrollTo(0, 0);
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mMenuWidth, 0);//初始化位置是content内容页面
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, getResources().getDisplayMetrics());
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //滑动回调，获取滑动距离，设置缩放等滑动的时候的动画。
        Log.e("TAG", "onScrollChanged-l:" + l);
        float scale = 1f * l / mMenuWidth;//scale 的值 1-- 0
        Log.e("TAG", "onScrollChanged-scale:" + scale);
        float rightScale = 0.7f + 0.3f * scale;//scale 的值 1-- 0,0.7+0.3 * scale 保证最小的view是0.7大小。
        Log.e("TAG", "onScrollChanged-rightScale:" + rightScale);

        ViewCompat.setPivotX(mContentView,0);//设置缩放点，默认是View的中心
        ViewCompat.setScaleY(mContentView,getMeasuredHeight()/2);
        ViewCompat.setScaleX(mContentView, rightScale);
        ViewCompat.setScaleY(mContentView, rightScale);

        float leftScale = 0.7f + 0.3f*(1-scale);
        //ViewCompat.setTranslationX(mMenuView,l*0.3f);
        ViewCompat.setPivotX(mMenuView,leftScale);
        ViewCompat.setScaleY(mMenuView,leftScale);
        //ViewCompat.
        float leftAlpha = 0.5f+0.5f*(1-scale);
        ViewCompat.setAlpha(mMenuView,leftAlpha);


    }
}
