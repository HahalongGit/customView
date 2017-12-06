package com.lll.beizertest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/11/28.
 *
 * @ClassName: ColorTrackTextView
 * @Description:
 * @Date 2017/11/28
 */

public class ColorTrackTextView extends android.support.v7.widget.AppCompatTextView {

    private int mOriginColor;

    private int mChangeColor;

    private final int ORIGIN_COLOR = Color.BLACK;
    private final int CHANGE_COLOR = Color.RED;

    private Paint mOriginPaint,mChangePaint;

    /**
     * 当前的进度
     */
    private float mCurrentProgress = 0.0f;

    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction{
        LEFT_TO_RIGHT,RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context,null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        mOriginColor = a.getColor(R.styleable.ColorTrackTextView_originColor,ORIGIN_COLOR);
        mChangeColor = a.getColor(R.styleable.ColorTrackTextView_changeColor,CHANGE_COLOR);
        a.recycle();

        mOriginPaint = new Paint();
        mOriginPaint.setAntiAlias(true);
        mOriginPaint.setColor(mOriginColor);
        mOriginPaint.setTextSize(getTextSize());

        mChangePaint = new Paint();
        mChangePaint.setAntiAlias(true);
        mChangePaint.setColor(mChangeColor);
        mChangePaint.setTextSize(getTextSize());
        //一个文字两种颜色，两个画笔
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int middle = (int) (mCurrentProgress*getWidth());
        //Log.e("TAG","onDraw-mCurrentProgress:"+mCurrentProgress);
        if(mDirection == Direction.LEFT_TO_RIGHT){//左到右，左边红色，右边黑色
            //Log.e("TAG","onDraw-LEFT_TO_RIGHT:");
            drawText(canvas,mChangePaint,0,middle);
            drawText(canvas,mOriginPaint,middle,getWidth());
        }else {
            //Log.e("TAG","onDraw-RIGHT_TO_LEFT:");
            drawText(canvas,mChangePaint,getWidth()-middle,getWidth());
            drawText(canvas,mOriginPaint,0,getWidth()-middle);
        }
    }

    public void setChangeColor(int changeColor){
        this.mChangeColor = changeColor;
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    public void  setCurrentProgress(float currentProgress){
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end){
        //Log.e("TAG","drawText执行");
        canvas.save();//剪裁画布的前面保存
        Rect rect = new Rect(start,0,end,getHeight());
        canvas.clipRect(rect);//裁剪区域,可以完成效果，左边和有右边用不同的画笔画出来,实现功能的关键***
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);
        int x = getWidth()/2- bounds.width()/2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseline = getHeight()/2+dy;
        canvas.drawText(text,x,baseline,paint);
        canvas.restore();
        //剪裁画布的后边释放
    }

}
