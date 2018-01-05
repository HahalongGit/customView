package com.lll.beizertest.view.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by longlong on 2018/1/5.
 *
 * @ClassName: LoadingView
 * @Description:
 * @Date 2018/1/5
 */

public class LoadingView extends RelativeLayout {

    private Context context;

    private CircleView leftCircle;

    private CircleView rightCircle;

    private CircleView centerCircle;

    private int MAX_DISTANCE = 20;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        leftCircle = getCircleView(context);
        leftCircle.changeColor(Color.BLUE);
        rightCircle = getCircleView(context);
        rightCircle.changeColor(Color.RED);
        centerCircle = getCircleView(context);
        centerCircle.changeColor(Color.GREEN);
        MAX_DISTANCE = px2dp(MAX_DISTANCE);
        addView(leftCircle);
        addView(rightCircle);
        addView(centerCircle);//最上面
        setBackgroundColor(Color.WHITE);
        post(new Runnable() {//View绘制完成开始动画
            @Override
            public void run() {
                openAnimator();
            }
        });
    }

    /**
     * 开始一个向两边运动的动画
     */
    private void openAnimator(){
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(leftCircle,"translationX",0,-MAX_DISTANCE);
        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(rightCircle,"translationX",0,MAX_DISTANCE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(300);
        animatorSet.playTogether(leftAnimator,rightAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                closeAnimator();
            }
        });
        animatorSet.start();
    }

    /**
     * 开始一个向中间运动的动画
     */
    private void closeAnimator(){
        final ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(leftCircle,"translationX",-MAX_DISTANCE,0);
        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(rightCircle,"translationX",MAX_DISTANCE,0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(300);
        animatorSet.playTogether(leftAnimator,rightAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                openAnimator();
                int leftColor = leftCircle.getColor();
                int rightColor = rightCircle.getColor();
                int centerColor = centerCircle.getColor();
                leftCircle.changeColor(rightColor);
                rightCircle.changeColor(centerColor);
                centerCircle.changeColor(leftColor);
            }
        });
        animatorSet.start();
    }

    public CircleView getCircleView(Context context) {
        CircleView circleView = new CircleView(context);
        RelativeLayout.LayoutParams params = new LayoutParams(px2dp(10), px2dp(10));//设置圆的大小
        params.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(params);
        return circleView;
    }

    private int px2dp(int radius) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(INVISIBLE);//这个一般使用一次，不管是什么，都设置INVISABLE,需要的我们在改
        //GONE 系统会重新执行绘制
        if(visibility==INVISIBLE){
            leftCircle.clearAnimation();
            rightCircle.clearAnimation();
            centerCircle.clearAnimation();
            ViewGroup parent = (ViewGroup) getParent();
            if(parent!=null){
                parent.removeView(this);//从布局移出LoadingView
            }
            removeAllViewsInLayout();//移出自己的子view
        }
    }
}
