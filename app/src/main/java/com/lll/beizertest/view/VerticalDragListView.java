package com.lll.beizertest.view;

import android.content.Context;
import android.print.PageRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

/**
 * Created by longlong on 2017/12/15.
 *
 * @ClassName: VerticalDragListView
 * @Description:
 * @Date 2017/12/15
 */

public class VerticalDragListView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;

    private View dragView;

    /**
     * 菜单高度
     */
    private int mMenuHeight;

    /**
     * 菜单是否打开
     */
    private boolean isoPenMenu;

    private View contenteView;

    private android.widget.TextView textView;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewDragHelper = ViewDragHelper.create(this, mDragCallBack);

    }

    //FrameLayout 怎么样拖动子View
    private ViewDragHelper.Callback mDragCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //指定那个子View是否可以拖动，对所有的子View都会执行一边
            //需求：
            // 1.指定列表可以拖动
            //2.列表只能垂直拖动
            //3.拖动的距离只能是后边View的高度（获取菜单View的高度，在测量方法执行后）
            //4.手指松开，打开还是关闭
            return dragView == child;//是dragListView 就拖动
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //垂直拖动移动的位置，返回值是一个top。不断处理top
            if (top < 0) {
                top = 0;
            }
            if (top >= mMenuHeight) {//最大是菜单的高度
                top = mMenuHeight;
            }
            return top;//返回滑动的距离
        }

//        @Override
//        public int clampViewPositionHorizontal(View child, int left, int dx) {
        // 水平拖动
//            return left;
//        }


        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //手指松开的时候,yvel 值是一个0
            if (releasedChild == dragView) {
                if (dragView.getTop() > mMenuHeight / 2) {
                    //打开，滚动到菜单的高度
                    mViewDragHelper.settleCapturedViewAt(0, mMenuHeight);
                    isoPenMenu = true;
                } else {
                    //滚动到0的位置，关闭
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                    isoPenMenu = false;
                }
                invalidate();
            }

        }
    };

    /**
     * 相应滚动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            //绘制流程结束后（onLayout 是绘制流程的最后一个方法）获取菜单view的高度
            mMenuHeight = getChildAt(0).getMeasuredHeight();//获取
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //加载完成布局获取View
        int childCount = getChildCount();
        if (childCount != 2) {
            new RuntimeException("VerticalDragListView 只能包含两个ziView");
        }
        contenteView = getChildAt(0);
        dragView = getChildAt(1);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    //现象：添加了一个ListView 后 ListView可以滑动，菜单不能滑动出来了。
    // ListView 已经调用了 getParent().requestDisallowInterceptTouchEvent(true); 请求父类不要拦截子类的事件，所以ListView 自己处理了，可以滑动
    // 我们这里处理拦截，把部分事件拦截处理。其实在源码中改变了mGroupFlag 的值
    //处理：
    private float mDownY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //向下滑动拦截，自己处理
        //ViewDragHelper: Ignoring pointerId=0 because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because
        //ViewDragHelper did not receive all the events in the event stream.(ListView 处理了DOWN 事件),需要处理Drag
        if (isoPenMenu) {
            return true;
            // TODO: 2017/12/15 拦截后item和子View不能点击 如何处理
            //return true;//如果菜单打开就拦截ListView 的操作，同时拦截了所有子View的操作，menu不能点击，这里有问题
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mViewDragHelper.processTouchEvent(ev);//让mViewDragHelper 处理整个事件
                mDownY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float moveY = ev.getY();
                //让ListView不做处理，滚动到了顶部，不让ListView 处理，拦截事件
                // moveY-mDownY>0 向下滑动
                if (moveY - mDownY > 0 && !canChildScrollUp()) {//canChildScrollUp ListView 向下滑到第一个条目的时候，才画出子View
                    return true;
                }
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

//    /**
//     * 是否还能向上滚动 26.0.1
//     * @return
//     */
//    public boolean canChildScrollUp() {
//
//        if (dragListView instanceof ListView) {
//            return ListViewCompat.canScrollList((ListView) dragListView, -1);
//        }
//        return dragListView.canScrollVertically(-1);
//    }

    /**
     * SwipeRefreshLayout 中判断子View是否到顶的操作。
     *
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 24.0.1 包
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (dragView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) dragView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(dragView, -1) || dragView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(dragView, -1);
        }
    }

}
