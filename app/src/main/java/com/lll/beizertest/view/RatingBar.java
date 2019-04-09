package com.lll.beizertest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/11/29.
 *
 * @ClassName: RatingBar
 * @Description:
 * @Date 2017/11/29
 */

public class RatingBar extends View {

    private Bitmap mStartNormal,mStartFocus;

    private int mGradeNumber;

    /**
     * 默认的星星数量
     */
    private int defaultStart = 5;

    /**
     * 选中的星星
     */
    private int mFocusNumber = 0;

    private OnClickStarListener onClickStarListener;

    public void setOnClickStarListener(OnClickStarListener onClickStarListener) {
        this.onClickStarListener = onClickStarListener;
    }

    public RatingBar(Context context) {
        this(context,null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int normalStart = a.getResourceId(R.styleable.RatingBar_startNormal,0);
        int focusStart = a.getResourceId(R.styleable.RatingBar_startFocus,0);
        mStartNormal = BitmapFactory.decodeResource(getResources(),normalStart);
        mStartFocus = BitmapFactory.decodeResource(getResources(),focusStart);

        mGradeNumber = a.getInteger(R.styleable.RatingBar_gradeNumber,defaultStart);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //图片的高度
        int height = mStartFocus.getHeight();
        int width = mStartFocus.getWidth()*mGradeNumber;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStar(canvas,mGradeNumber,mStartNormal);
        drawStar(canvas,mFocusNumber,mStartFocus);
    }

    private void drawStar(Canvas canvas,int num,Bitmap bitmap) {
        if(num>0){
            Log.e("TAG","drawStar-num:"+num);
            for(int i=0;i<num;i++){
                canvas.drawBitmap(bitmap,i*mStartNormal.getWidth(),0,null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            //减少onDraw 的调用
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                //根据当前的位置计算出当前要画出的星星数量，然后刷新重绘制就好了
                float moveX = event.getX();//event.getX(); 相对于父布局的距离，event.getRawX() 相对于屏幕的位置
                int bitmapWidth = mStartNormal.getWidth();
                int number = (int) (moveX / bitmapWidth)+1;
                if(number<0){
                    mFocusNumber = 0;
                }
                Log.e("TAG","onTouchEvent-X1:"+moveX+",NUM:"+number+",mFocusNumber:"+mFocusNumber);
                if(number==mFocusNumber){
                    return true;//相等的时候不去绘制
                }
                if(number>mGradeNumber){
                    mFocusNumber = mGradeNumber;
                }else {
                    mFocusNumber = number;
                }
                invalidate();
                Log.e("TAG","onTouchEvent-X2:"+moveX+",NUM:"+number+",mFocusNumber:"+mFocusNumber);
                break;
            }
            case MotionEvent.ACTION_UP:{
                if(onClickStarListener!=null){
                    onClickStarListener.onClickStar(mFocusNumber);
                }
                break;
            }
        }
        return true;
    }

    /**
     * 点击星星
     */
    public interface OnClickStarListener{
        /**
         * 选中的位置
         * @param star
         */
        void onClickStar(int star);

    }

}
