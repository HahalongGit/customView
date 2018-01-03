package com.lll.beizertest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/12/11.
 *
 * @ClassName: DrawLayout
 * @Description: 侧滑菜单布局
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

    /**
     * 手势操作
     */
    private GestureDetector gestureDetector;

    /**
     * 是否打开
     */
    private boolean isOpend;

    /**
     * 是否拦截
     */
    private boolean isIntercept;

    private GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //只关注快速滑动的方法,只要快速滑动就会有回调
            //打开的时候往右边快速滑动（关闭），关闭的时候往左边快速滑动（打开）

            //快速 velocityX 往左边滑动是一个负数，往右边滑动是一个正数
            if (isOpend) {
                //打开的时候往右边快速滑动（关闭）
                if (velocityX < 0) {
                    closeMenu();
                    Log.e("TAG", "closeMenu-velocityX<0:");
                    return true;
                }
            } else {
                if (velocityX >= 0) {
                    Log.e("TAG", "closeMenu-velocityX>=0:");
                    openMenu();
                    return true;//onFling 返回true给gestureDetector.onTouchEvent(ev)
                }
            }
            Log.e("TAG", "velocityX:" + velocityX);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

    };

    //两个问题：
    //1.快速滑动（手指快速滑动打开或者关闭菜单）
    //2.事件拦截（打开菜单后右边的view不能点击，打开时点击关闭菜单）

    //QQ的效果和这个的差异：（修改）

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
        gestureDetector = new GestureDetector(context, onGestureListener);//创建一个快速滑动监听
    }


    /**
     * 布局加载完毕再回回调的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // onFinishInflate LayoutInflate.inflate()后调用
        //指定content的宽度是内容的宽度
        //制定menu的宽度是muenu减去属性的宽度

        //这里可以获取View 操作View设置View的属性
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
        if (isIntercept) {
            //有拦截就不执行自己的onTouchEvent
            return true;
        }
        //添加手指快速滑动Gesturedetector
        if (gestureDetector.onTouchEvent(ev)) {//回调gestureDetector返回true
            //如果快速滑动执行了（打开或者关闭了）下面 onTouchEvent 就不要执行了
            Log.e("TAG", "onTouchEvent-gestureDetector");
            return true;
        }
        // getScrollX(); 滑动的距离和 onScrollChanged l 的距离一样
        Log.e("TAG", "onTouchEvent:" + getScrollX());
        if (MotionEvent.ACTION_UP == ev.getAction()) {
            if (getScrollX() < mMenuWidth / 2f) {
                openMenu();
            } else {
                closeMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isIntercept = false;
        if (isOpend) {
            //打开时拦截事件
            float currentX = ev.getX();
            Log.e("TAG","onInterceptTouchEvent-currentX:"+currentX);
            Log.e("TAG","onInterceptTouchEvent-mMenuWidth:"+mMenuWidth);
            if (currentX > mMenuWidth) {
                closeMenu();
                //子View不需要相应事件，给关闭菜单
                isIntercept = true;
                return true;
                //true表示拦截子view事件，会相应自己的（本View）的onTouchEvent,这里又会执行本列的onTouchEvent 操作打开和关闭，这里不需要执行
            }

        }
        return super.onInterceptTouchEvent(ev);
    }

    private void openMenu() {
        smoothScrollTo(0, 0);
        isOpend = true;
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        isOpend = false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //onLayout view的绘制流程中的最后一个方法。（onMeasure ,onLayout、onDraw）view 的绘制流程是在在onResumt 方法之后调用的。
        //可以在ViewRootImpl中查看（requestLayout方法）
        scrollTo(mMenuWidth, 0);//初始化位置是content内容页面
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, getResources().getDisplayMetrics());
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // onScrollChanged --范围 mMenuWidth -->0
        //滑动回调，获取滑动距离，设置缩放等滑动的时候的动画。
        Log.e("TAG", "onScrollChanged-l:" + l);
        float scale = 1f * l / mMenuWidth;//scale 的值 1-- 0
        Log.e("TAG", "onScrollChanged-scale:" + scale);
        float rightScale = 0.7f + 0.3f * scale;//scale 的值 1-- 0；0.7+0.3 * scale 保证最小的view是0.7大小。
        Log.e("TAG", "onScrollChanged-rightScale:" + rightScale);

        //左右的缩放，移动，透明动画处理。(ViewCompat 是一个ViewCompat兼容处理)
        ViewCompat.setPivotX(mContentView, 0);//设置缩放点，默认是View的中心
        ViewCompat.setPivotY(mContentView, getMeasuredHeight() / 2);
        ViewCompat.setScaleX(mContentView, rightScale);
        ViewCompat.setScaleY(mContentView, rightScale);

        float leftScale = 0.7f + 0.3f * (1 - scale);
        ViewCompat.setTranslationX(mMenuView, l * 0.2f);//设置平移
        ViewCompat.setPivotX(mMenuView, leftScale);
        ViewCompat.setScaleY(mMenuView, leftScale);
        //ViewCompat.
        float leftAlpha = 0.5f + 0.5f * (1 - scale);
        ViewCompat.setAlpha(mMenuView, leftAlpha);
    }
}
