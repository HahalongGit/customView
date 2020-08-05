package com.lll.beizertest.draw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义ViewGroup 测量 布局测试案例
 *
 * @author RunningDigua
 * @date 2020/8/5
 */
public class MyLayout extends ViewGroup {

    private static final String TAG = "MyLayout";

    private boolean isCenter;

    public MyLayout(Context context) {
        this(context, null);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        int height = 0;
        int width = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            height += childHeight;
            width = Math.max(childWidth, width);
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthMeasureSize : width,
                (heightMode == MeasureSpec.EXACTLY) ? heightMeasureSize : height);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.e(TAG, "onLayout-l:" + l);
        Log.e(TAG, "onLayout-t:" + t);
        Log.e(TAG, "onLayout-r:" + r);
        Log.e(TAG, "onLayout-b:" + b);
        int top = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            Log.e(TAG, "onLayout-measuredWidth:" + childWidth);
            Log.e(TAG, "onLayout-measuredHeight:" + childHeight);
            int width = getMeasuredWidth();
            Log.e(TAG, "onLayout-layout-width:" + width);
            // 剧中显示
            int centerOffset =0;
            if(isCenter){//是否剧中
                centerOffset = (getMeasuredWidth()-childView.getMeasuredWidth())/2;
            }
            top+=lp.topMargin;
            // 添加了margin之后，childView的位置要有偏移margin，top先加topMargin，然后放好childView后在加上bottomMargin
            // 添加了 leftMargin 开始的位置也要偏移
            childView.layout(centerOffset+lp.leftMargin, top, centerOffset+childWidth+lp.leftMargin, top + childHeight);
            top += childHeight+lp.bottomMargin;

            Log.e(TAG, "onLayout-top:" + top);
        }
    }
}
