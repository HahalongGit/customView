package com.lll.beizertest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lll.beizertest.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

/**
 * Created by longlong on 2017/11/22.
 *
 * @ClassName: BubbleMessageTouchListener
 * @Description:
 * @Date 2017/11/22
 */

public class BubbleMessageTouchListener implements View.OnTouchListener,
        MessageBubbleView.OnMessageBubbleListener {

    /**
     * 原来要拖动的view
     */
    private View mStaticView;

    private WindowManager mWindowManager;

    private MessageBubbleView mMessageBubbleView;

    private WindowManager.LayoutParams mParams;

    private Context mContext;

    /**
     * 爆炸容器
     */
    private FrameLayout mBombFrame;

    /**
     * 爆炸Image
     */
    private ImageView mBombImage;

    public BubbleMessageTouchListener(View mStaticView, Context context) {
        this.mStaticView = mStaticView;
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mMessageBubbleView = new MessageBubbleView(context);
        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSPARENT;
        mMessageBubbleView.setOnMessageBubbleListener(this);
       // mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mBombFrame = new FrameLayout(context);
        mBombImage = new ImageView(context);
        mBombImage.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
        mBombFrame.addView(mBombImage);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                //按下把当前view隐藏，复制一个进行拖动
                mStaticView.setVisibility(View.INVISIBLE);

                //在WindowManager 上复制一个View的Bitmap 绘制显示出来
                mWindowManager.addView(mMessageBubbleView,mParams);//
                //初始化贝塞尔 view的点,
                int location[]= new int[2];
                mStaticView.getLocationOnScreen(location);//获取view在屏幕上的位置
                mMessageBubbleView.intPoint(location[0]+mStaticView.getWidth()/2,location[1]+mStaticView.getHeight()/2-BubbleUtils.getStatusBarHeight(mContext));
                mMessageBubbleView.setDragBitmap(getDragViewBitmap(mStaticView));
                Log.e("TAG","intPointX"+event.getX());
                Log.e("TAG","intPointRawx"+event.getRawX());
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                //更新贝塞尔
                Log.e("TAG","intPointY-:"+event.getY());
                Log.e("TAG","intPointRawY-:"+event.getRawY());
                Log.e("TAG","updateDragPoint");
                mMessageBubbleView.updateDragPoint(event.getRawX(),event.getRawY()-BubbleUtils.getStatusBarHeight(mContext));
                break;
            }
            case MotionEvent.ACTION_UP:{
                //松开如果贝塞尔曲线没有消失就回弹
                mMessageBubbleView.handleActionUp();
                break;
            }
        }

        return true;
    }

    /**
     * 通过View 获取Bitmap
     * @param view
     * @return
     */
    private Bitmap getDragViewBitmap(View view) {
        Bitmap bitmap = null;
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();
//        try {
//            int width = view.getWidth();
//            int height = view.getHeight();
//            if(width != 0 && height != 0){
//                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                view.layout(0, 0, width, height);
//                view.draw(canvas);
//            }
//        } catch (Exception e) {
//            bitmap = null;
//            e.getStackTrace();
//        }
        return bitmap;
    }

    @Override
    public void restore() {
        //恢复view显示
        mWindowManager.removeView(mMessageBubbleView);
        mStaticView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss(PointF pointF) {
        //爆炸消失，帧动画
        mWindowManager.removeView(mMessageBubbleView);
        //WindowManager 上添加一个爆炸动画,包装一层布局，不然ImageView是全屏的'

        mWindowManager.addView(mBombFrame,mParams);
        mBombImage.setBackgroundResource(R.drawable.anim_bubble_pop);
        AnimationDrawable animatorDrawable = (AnimationDrawable) mBombImage.getBackground();
        mBombImage.setX(pointF.x-animatorDrawable.getIntrinsicWidth()/2);//设置消失的位置
        mBombImage.setY(pointF.y-animatorDrawable.getIntrinsicHeight()/2);
        animatorDrawable.start();
        //执行完成移除
        mBombImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWindowManager.removeView(mBombFrame);
                //通知view消失，设置给attach的页面，可能调接口该数据等
            }
        },getAnimationDrawableTime(animatorDrawable));
    }

    /**
     * 获取动画的时间
     * @param animatorDrawable
     * @return
     */
    private long getAnimationDrawableTime(AnimationDrawable animatorDrawable) {
        int numberOfFrames = animatorDrawable.getNumberOfFrames();
        long time = 0;
        for(int i=0;i<numberOfFrames;i++){
            time += animatorDrawable.getDuration(i);
        }
        return time;
    }
}
