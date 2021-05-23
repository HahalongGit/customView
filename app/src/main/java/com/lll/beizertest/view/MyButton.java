package com.lll.beizertest.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

/**
 * 理解Shader 中RadicalGrident 的使用，如何实现按钮的水波效果
 *
 * 实现步骤：
 * 1.手指按下的时候绘制一个默认的圆
 * 2.设置圆可以随手指移动
 * 3.创建 RadicalGrident 的 渐变 设置TitleMode模式为CLAMP使用边缘的颜色 填充空白区域,
 *   从透明的默认小圆到View的最大宽或者高（对比） 设置Paint
 * 4.设置属性动画，让绘制的圆的半径从默认大小到最大的值
 * 5.在手指按下时动态绘制
 * @author RunningDigua
 * @date 2020/8/4
 */
public class MyButton extends AppCompatButton {

    private static final String TAG = "MyButton";

    private Paint mPaint;

    private int mX;
    private int mY;

    private int mCurRadius = 0;

    private int DEFAULT_RADIUS = 50;

    private RadialGradient mRadialGradient;

    private ObjectAnimator mObjectAnimator;

    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mCurRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mX != event.getX() || mY != event.getY()) {
            mX = (int) event.getX();
            mY = (int) event.getY();
            setRadius(DEFAULT_RADIUS);
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {// 不拦截down 事件，UP实践就不会传递回来
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
                mObjectAnimator.cancel();
            }
            if (mObjectAnimator == null) {
                Log.e(TAG, "mObjectAnimator-radius");
                mObjectAnimator = ObjectAnimator.ofInt(this, "radius", DEFAULT_RADIUS, getWidth());
            }
            mObjectAnimator.setInterpolator(new AccelerateInterpolator());
            mObjectAnimator.setDuration(500);
            mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    Log.e(TAG, "mObjectAnimator-animatedValue" + animatedValue);
                }
            });
            mObjectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setRadius(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mObjectAnimator.start();
        }

        return super.onTouchEvent(event);
    }

    private void setRadius(int radius) {
        mCurRadius = radius;
        if (mCurRadius > 0) {
            mRadialGradient = new RadialGradient(mX, mY, radius, 0x00FFFFFF, 0xFF58FAAC, Shader.TileMode.CLAMP);
            mPaint.setShader(mRadialGradient);
        }
        postInvalidate();
    }

}
