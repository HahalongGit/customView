package com.lll.beizertest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/11/27.
 *
 * @ClassName: TextView
 * @Description:
 * @Date 2017/11/27
 */

public class TextView extends View {
    //继承View，继承LinearLayout 不会出现效果，不会触发onDraw方法。但是添加了background可以出现效果//
    //view 源码 19088 行 public void draw(Canvas canvas) {
    /**
     * 获取Text
     */
    private String mText;

    /**
     * text大小
     */
    private int mTextSize = 15;

    /**
     * text颜色
     */
    private int mTextColor = Color.BLACK;

    /**
     * 默认大小
     */
    private int textSize = 15;

    /**
     * 画笔
     */
    private Paint mPaint;

    public TextView(Context context) {
        this(context,null);
    }

    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        //系统定义过的属性不能再定义。text,textColor等
        mText = array.getString(R.styleable.TextView_hahaText);
        mTextColor = array.getColor(R.styleable.TextView_hahaTextColor,Color.BLACK);
        mTextSize = array.getDimensionPixelSize(R.styleable.TextView_hahaTextSize,sp2px(textSize));
        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);//设置字体的大小
        mPaint.setColor(mTextColor);//字体的颜色
    }

    /**
     * sp 转换成px
     * @param sp
     * @return
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 1 给确定的值，不需要计算，给多少就是多少 100dp 等，不会去计算
        // 2.wrap_content ,要计算字的宽高 AT_MOST，需要自己计算
        // 3.match_parent 不需要计算
        if(widthMode == MeasureSpec.AT_MOST){
            //计算字的宽高，字体的长度有关，和字的大小有关
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width = bounds.width()+getPaddingLeft()+getPaddingRight();//测量字的大小
        }
        if(heightMode == MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height = bounds.height()+getPaddingTop()+getPaddingBottom();
        }
        setMeasuredDimension(width,height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //继承 ViewGroup不会绘制，除非添加了的boakground，在setBackground中出行设置（computeOpaqueFlag）opaque 这个值
        // onDraw 中有Flag 判断，onDispatchDraw()和其他没有Flag判断的方法，可以替换onDraw();
        //x就是开始的位置，可能有padding等，y就是基线baseLine，高度的一半是不对的，求baseLine
        //top(负值) bottom（正直） 除以2就是中心，减去bottom就是dy
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2+dy;//dy就是高度一半到baseline的距离
        int x = getPaddingLeft();//开始位置在padding
        canvas.drawText(mText,x,baseLine,mPaint);
    }
}
