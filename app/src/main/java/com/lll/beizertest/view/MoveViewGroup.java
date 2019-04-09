package com.lll.beizertest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by longlong on 2019/4/9.
 *
 * @ClassName: MoveViewGroup
 * @Description: ViewDragHelper 实现滑动
 * @Date 2019/4/9
 */

public class MoveViewGroup extends FrameLayout {

    // 采用ViewDrawHelper 实现滑动的方式讲解：
    // https://www.cnblogs.com/fuly550871915/p/4985867.html
    private static final String TAG = "MoveViewGroup";

    private View mFirstView;
    private View mSecondView;

    private int mFirstViewWidth;

    private ViewDragHelper viewDragHelper;

    public MoveViewGroup(Context context) {
        this(context, null);
    }

    public MoveViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                //该方法是告诉回调的callback什么时候开始检测触摸事件。因为ViewDragHelper定义在一个
//                ViewGroup中，比如你需要当触摸到第二个子view时，才开始检测触摸事件，就可以如上书写。
//                return mSecondView == child; // 触摸第二个View的时候滑动
                return mSecondView == child;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//                该方法控制水平方向的移动，默认返回为0，即不移动.如果你想让水平方向上发生移动，可以像上面那样进行书写 返回left。
                Log.e(TAG, "clampViewPositionHorizontal-Left:" + left);
                int returnLeft = 0;
                if (left < 0) {// 向左边滑动最小不能小于0
                    returnLeft = 0;
                } else if (left > 500) {
                    returnLeft = 500;// 右边滑动最大不超过500
                } else {
                    returnLeft = left;
                }
                return returnLeft;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                // 垂直方向的滑动，和上面的水平一样，这里返回0
                return 0;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //注意：该方法在手指抬起或者说是拖动结束时调用。可以在此方法中写一些你需要处理的逻辑。
                if (mSecondView.getLeft() < 200) {
                    //如果第二个view的left小于200像素，就不显示第一个view。下面的代码就相当于此
                    viewDragHelper.smoothSlideViewTo(mSecondView, 0, 0);// 滑动的方法
                    ViewCompat.postInvalidateOnAnimation(MoveViewGroup.this);// 滑动完成刷新 这两个方法配合使用
                } else {
                    //显示第一个view 500
                    viewDragHelper.smoothSlideViewTo(mSecondView, 500, 0);
                    ViewCompat.postInvalidateOnAnimation(MoveViewGroup.this);
                }
            }

//            另外还有一些其他方法，介绍如下：
//            onViewCaptured()        这个事件在用户触摸到view后回调
//            onViewDragStateChanged()      这个事件在拖拽状态改变时回调，比如idle,dragging等状态
//            onViewPositionChanged()       这个事件在位置改变时回调，常用于滑动时更改scale进行缩放等效果。

            //至此，基本上使用ViewDragHelper进行滑动的方法就讲解完了。但是还要记得在ViewGroup中，要在onFinishInflate方法中将子view获取到。
//
//            另外，ViewDragHelper使用smoothSlideViewTo进行滑动，
//            使用ViewCompat.postInvalidateOnAnmation方法进行刷新。这两个方法一定要同时使用！

            // 代码
//             if(mSecondView.getLeft() <100)
//            {//如果第二个view的left小于500像素，就不显示第一个view。下面的代码就相当于此
//                mViewDragHelper.smoothSlideViewTo(mSecondView, 0, 0);
//                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
//            }else
//            {//显示第一个view
//                mViewDragHelper.smoothSlideViewTo(mSecondView, 300, 0);
//                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
//            }
//            结合前面的基础知识和代码中的注释，应该很好理解了。就是在DragViewGroup里面使用ViewDragHelper定义了一个拖动的效果而已！
//            值得注意的是在onReleased方法中，我们做了这样子的一个设定，即当mSecondView的左边距小于100像素时，
//            就将mSecondView移动到初始位置（造成的效果仍然是遮挡出mFirstView，即关闭mFirstView）；当其左边与大于100像素，
//            就将其移动的距离左边300像素的位置（当然是相对于它的ViewGroup的左边距了）。
        });
    }


    /**
     * 布局加载完成后调用
     * 在这个方法里获取子view
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 获取View
        mFirstView = getChildAt(0);
        mSecondView = getChildAt(1);
    }

    /**
     * 子view的大小改变后回调该方法
     * 在这个方法里获取到mFirstView的宽度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mFirstViewWidth = mFirstView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);// 拦截事件
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);// 传递事件给ViewDrawHelper
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
//        ViewDragHelper内部也是通过Scroller类实现平滑滑动的，因此它也要重写这个方法。按照下面的模板书写即可
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);// 刷新View
        }
    }
}
