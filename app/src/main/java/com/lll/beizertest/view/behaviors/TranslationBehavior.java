package com.lll.beizertest.view.behaviors;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by longlong on 2017/12/19.
 *
 * @ClassName: TranslationBehavior
 * @Description: 实现某个效果的Behavior
 * @Date 2017/12/19
 */

public class TranslationBehavior extends CoordinatorLayout.Behavior {

    private boolean isOut;

    private boolean isEnd = true;

    public TranslationBehavior() {
        super();
    }

    public TranslationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;//是否垂直滚动，true 后边的方法才执行！
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        //dyConsumed 上下滑动的距离
        Log.e("TAG","onNestedScroll-dyConsumed:"+dyConsumed);
        if(dyConsumed>0){
            if(!isOut){
                if(isEnd){
                    int translationY = child.getMeasuredHeight()+((CoordinatorLayout.LayoutParams)child.getLayoutParams()).bottomMargin;
                    Log.e("TAG","onNestedScroll-translationY:"+translationY);
                    child.animate().translationY(translationY).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isEnd = true;
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isEnd = false;
                        }
                    }).start();
                    isOut = true;
                }
            }
        }

        if(dyConsumed<0) {
            if(isOut){
                if(isEnd){
                    child.animate().translationY(0).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isEnd = true;
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isEnd = false;
                        }
                    }).start();
                    isOut = false;
                }

            }
        }
    }
}
