package com.lll.beizertest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ViewAnimator;

/**
 * Created by longlong on 2017/11/20.
 *
 * @ClassName: MessageBubble
 * @Description:
 * @Date 2017/11/20
 */

public class MessageBubbleView extends View {

    /**
     * 起始的点和拖拽后的点
     */
    private PointF mFixationPoint, mDragPoint;

    /**
     * 拖拽的圆半径
     */
    private int mDragRadius = 10;

    private Paint mPaint;

    /**
     * 起始点半径
     */
    private int mFixationRadius;

    /**
     * 起始点最大半径
     */
    private int mFixationRadiusMax = 7;

    /**
     * 两个点的距离
     */
    private double distance = 0f;

    /**
     * 拖拽后原始变到最小的半径
     */
    private int mFixationRadiusMin = 4;

    /**
     * 绘制bitmap
     */
    private Bitmap mBitmap;

    private OnMessageBubbleListener onMessageBubbleListener;

    public void setOnMessageBubbleListener(OnMessageBubbleListener onMessageBubbleListener) {
        this.onMessageBubbleListener = onMessageBubbleListener;
    }

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragRadius = dip2px(mDragRadius);
        mFixationRadiusMax = dip2px(mFixationRadiusMax);
        mFixationRadiusMin = dip2px(mFixationRadiusMin);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);//什么意思
    }

    private int dip2px(int mDragRadius) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDragRadius, getResources().getDisplayMetrics());
    }
//onTouch 在外处理，此处注释
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                float downX = event.getX();
//                float downY = event.getY();
//                intPoint(downX, downY);
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                float moveX = event.getX();
//                float moveY = event.getY();
//                updateDragPoint(moveX, moveY);
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//
//                break;
//            }
//        }
//        invalidate();//点击或者移动的操作后就重新绘制
//        return true;
//    }

    public void updateDragPoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
        invalidate();//点击或者移动的操作后就重新绘制
    }

    public void intPoint(float x, float y) {
        mFixationPoint = new PointF(x, y);
        mDragPoint = new PointF(x, y);
//        invalidate();//点击或者移动的操作后就重新绘制
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFixationPoint == null || mDragPoint == null) {
            return;
        }
        Log.e("TAG", "onDraw");
        //固定圆有一个初始大小，随着拖动的距离，到一定的大小会消失
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);
        distance = getDistance(mFixationPoint, mDragRadius);
        Log.e("TAG", "onDraw-distance:" + distance);
        mFixationRadius = (int) (mFixationRadiusMax - distance / 14);
        Log.e("TAG", "onDraw-mFixationRadius:" + mFixationRadius);
        Path bezierPath = getBeizerPath(mDragPoint, mFixationPoint);
        if (bezierPath != null) {
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
            canvas.drawPath(bezierPath, mPaint);//绘制路径

            //添加绘制图片
            if(mBitmap!=null){
                //画图片，位置和手指的位置一样，绘制bitmap添加一个渐变动画可以避免突兀的出现图片
                canvas.drawBitmap(mBitmap,mDragPoint.x-mBitmap.getWidth()/2,mDragPoint.y-mBitmap.getHeight()/2,mPaint);
            }
        }
    }

    private Path getBeizerPath(PointF mDragPoint, PointF mFixationPoint) {
        Path path = new Path();
        distance = getDistance(mFixationPoint, mDragRadius);
        Log.e("TAG", "onDraw-distance:" + distance);
        mFixationRadius = (int) (mFixationRadiusMax - distance / 14);
        Log.e("TAG", "onDraw-mFixationRadius:" + mFixationRadius);
        if (mFixationRadius < mFixationRadiusMin) {
            return null;
        }
        float dy = mDragPoint.y - mFixationPoint.y;
        float dx = mDragPoint.x - mFixationPoint.x;
        float tan = dy / dx;
        double arcTan = Math.atan(tan);//根据斜率（正切值） 计算弧度，（计算需要弧度值）

        Log.e("TAG","MessageBubbleView2-sin:"+Math.sin(Math.PI/6));//Math.PI/6 是30 °对应的弧度值，0.49999999999999994≈0.5

        float p0X = (float) (mFixationPoint.x + mFixationRadius * Math.sin(arcTan));//需要一个弧度
        float p0Y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(arcTan));

        float p1X = (float) (mDragPoint.x + mDragRadius * Math.sin(arcTan));
        float p1Y = (float) (mDragPoint.y - mDragRadius * Math.cos(arcTan));

        float p2X = (float) (mDragPoint.x - mDragRadius * Math.sin(arcTan));
        float p2Y = (float) (mDragPoint.y + mDragRadius * Math.cos(arcTan));

        float p3X = (float) (mFixationPoint.x - mFixationRadius * Math.sin(arcTan));
        float p3Y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(arcTan));

        PointF contralPoint = getContralPoint(mDragPoint, mFixationPoint);
        path.moveTo(p0X, p0Y);//移动到指定位置，原始位置在（0,0）点
        path.quadTo(contralPoint.x, contralPoint.y, p1X, p1Y);//以第一次点为控制点画到目的一个点的曲线
        path.lineTo(p2X, p2Y);//链接到这个点，从上一个点
        path.quadTo(contralPoint.x, contralPoint.y, p3X, p3Y);//以第一个点画第二个曲线，
        path.close();
        return path;
    }

    private PointF getContralPoint(PointF mDragPoint, PointF mFixationPoint) {
        return new PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2);
    }

    private double getDistance(PointF mFixationPoint, int mDragRadius) {
        return Math.sqrt((mFixationPoint.x - mDragPoint.x) * (mFixationPoint.x - mDragPoint.x) + (mFixationPoint.y - mDragPoint.y) * (mFixationPoint.y - mDragPoint.y));
    }

    /**
     * 绑定view
     * @param view
     * @param
     */
    public static void attachView(View view) {
        view.setOnTouchListener(new BubbleMessageTouchListener(view,view.getContext()));
    }

    public void setDragBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    /**
     * 处理手指抬起（回弹和爆炸）
     */
    public void handleActionUp() {

        if(mFixationRadius > mFixationRadiusMin){
            //回弹
            final PointF start = new PointF(mDragPoint.x,mDragPoint.y);
            final PointF end = new PointF(mFixationPoint.x,mFixationPoint.y);
            ValueAnimator animator = ObjectAnimator.ofFloat(1);
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    Log.e("TAG","onAnimationUpdate:"+value);
                    PointF pointF = BubbleUtils.getPointByPercent(start,end,value);
                    updateDragPoint(pointF.x,pointF.y);
                }
            });
            //设置插值器，结束时候回弹
            animator.setInterpolator(new OvershootInterpolator(3f));
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if(onMessageBubbleListener!=null){
                        onMessageBubbleListener.restore();
                    }
                }
            });
        }else {
            //爆炸
            if(onMessageBubbleListener!=null){
                //在拖拽的地方爆炸
                onMessageBubbleListener.dismiss(mDragPoint);
            }
        }

    }

    /**
     * 一次拖动结束设置消失复制的Bitmap
     */
    public interface OnMessageBubbleListener{

        void restore();

        void dismiss(PointF pointF);

    }

}
