package com.lll.beizertest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by longlong on 2019/4/9.
 *
 * @ClassName: MoveView
 * @Description: 滑动View
 * @Date 2019/4/9
 */

public class MoveView extends View {

    private Scroller mScroller;

    private ViewDragHelper viewDragHelper;//

    private static final String TAG = "MoveView";

    private int lastX;

    private int lastY;

    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int x = (int) event.getX();
        Log.e(TAG, "onTouchEvent-x:" + x);
        Log.e(TAG, "onTouchEvent-y:" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = x;
                lastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int offseX = x - lastX;
                int offseY = y - lastY;
                Log.e(TAG, "offseX:" + offseX);
                Log.e(TAG, "offseY:" + offseY);
                // 1
//                layout(getLeft() + offseX, getTop() + offseY, getRight() + offseX, getBottom() + offseY);
//                 2
//                offsetLeftAndRight(offseX);
//                offsetTopAndBottom(offseY);
                // 3
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                ViewGroup.LayoutParams params = getLayoutParams(); // MarginLayoutParams 重写了有margin属性
//                params.leftMargin = getLeft() + offseX;
//                params.topMargin = getTop() + offseY;
//                setLayoutParams(params);
                // 4
//                ((View) getParent()).scrollTo(-x, -y);// 终点坐标  ????
//                ((View)getParent()).scrollBy(-offseX, -offseY);// 增量的相反数

                // 5 Scroller 的用法 Scroller 的滑动不是即时的在手指滑动结束后渐渐平滑的滑动到手指的位置
                // 5.1但却经常用它来实现手指抬起后，view回到初始位置
//                mScroller.startScroll(((View) getParent()).getScrollX(), ((View) getParent()).getScrollY(), -offseX, -offseY);  // scrollTo 到底怎么用的
                //开启滑动,抬起的时候让其回到原点
//                ((View) getParent()).scrollBy(-offseX, -offseY);

                // 6 重新定义 在ViewGroup 中
                break;
            }
            // 5.1 Scroller 平滑滑动原位测试
//            case MotionEvent.ACTION_UP: {// 手指抬起的时候回去原来位置
//                View viewGroup = (View) getParent();
//                mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY());
//                break;
//            }
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 5 和 5.1
//        if (mScroller.computeScrollOffset()) {
//            Log.e(TAG, "computeScroll-x:" + mScroller.getCurrX());
//            Log.e(TAG, "computeScroll-y:" + mScroller.getCurrY());
//            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//        }
//        invalidate();
    }
}
