package com.lll.beizertest.view.nestedscrolling;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 测试NestedScrollingParent2 和 NestedScrollingChild2
 * <p>
 * 事件分发是这样的：子View首先得到事件处理权，处理过程中，父View可以对其拦截，
 * 但是拦截了以后就无法再还给子View（本次手势内）
 * <p>
 * NestedScrolling机制是这样的：内部View在滚动的时候，首先将dx,dy交给NestedScrollingParent，
 * NestedScrollingParent可对其进行部分消耗，剩余的部分还给内部View。
 *
 * @author runningDigua
 * @date 2020/1/3
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent2 {

    private static final String TAG = "StickyNavLayout";

    private int mTopViewHeight = 100;

    private Scroller mScroller;

    public StickyNavLayout(Context context) {
        this(context, null);
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyNavLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    int allDy = 0;

    /**
     * View 内部什么时候回调 NestedScrollingParent 的方法？ onTouchEvent()中
     *
     * @param target
     * @param dx
     * @param dy
     * @param consumed Output. The horizontal and vertical scroll distance consumed by this parent
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.e(TAG, "onNestedPreScroll-dx:" + dx);
        allDy += dy;
        Log.e(TAG, "onNestedPreScroll-allDy:" + allDy);//View 滑动完成 加起来 的dy 是View的高度 mTopViewHeight
        Log.e(TAG, "onNestedPreScroll-dy:" + dy);// 一次滑动 scroll distance in pixels
        Log.e(TAG, "onNestedPreScroll-scrollY:" + getScrollY());//滑动的距离

//        onNestedPreScroll中，我们判断，如果是上滑且顶部控件未完全隐藏，则消耗掉dy，即consumed[1]=dy;
//        如果是下滑且内部View已经无法继续下拉，则消耗掉dy，即consumed[1]=dy，消耗掉的意思，就是自己去执
//        行scrollBy，实际上就是我们的StickNavLayout滑动。
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);//当前View（Parent）滑动dy距离，所以Bottom会露出空白，如何处理
            consumed[1] = dy;//指定Parent消耗滑动距离
        }
    }

    /**
     * 这里还处理了fling，通过onNestedPreFling方法，这个可以根据自己需求定了，当顶部控件显示时，fling可以让顶部控件隐藏或者显示。
     *
     * @param target
     * @param velocityX
     * @param velocityY
     * @return return true if this parent consumed the fling ahead of the target view
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e(TAG, "onNestedPreFling-velocityY:" + velocityY);
        Log.e(TAG, "onNestedPreFling-velocityX:" + velocityX);
        if (getScrollY() >= mTopViewHeight) {
            return false;
        }
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        //获取View的宽高
        mTopViewHeight = getChildAt(0).getMeasuredHeight();
    }

    /**
     * 重写 进行边界检测
     *
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

}
