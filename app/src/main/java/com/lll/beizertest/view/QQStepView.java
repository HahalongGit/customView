package com.lll.beizertest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/11/28.
 *
 * @ClassName: QQStepView
 * @Description:
 * @Date 2017/11/28
 */

public class QQStepView extends View {

    /**
     * 外层圆弧颜色
     */
    private int mOuterColor;

    /**
     * 内层圆弧颜色
     */
    private int mInnerColor;

    /**
     * 字体大小
     */
    private int mTextSize;

    /**
     * 字体颜色
     */
    private int mTextColor;

    /**
     * 圆弧宽度
     */
    private int mBorderWidth;

    /**
     * 默认5
     */
    private int borderWidth  = 5;

    /**
     *
     */
    private int OUTER_COLOR = Color.BLUE;

    private int INNER_COLOR = Color.RED;

    private int TEXT_COLOR = Color.BLACK;

    private Paint mPaint,mInnerPaint,mTextPaint;


    /**
     * 总共的步数
     */
    private int mStepMax;

    /**
     * 当前的步数
     */
    private int mCurrentStep;

    /**
     * 设置最大步数
     * @param mStepMax
     */
    public synchronized void setStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }

    /**
     * 设置当前步数
     * @param mCurrentStep
     */
    public synchronized void setCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
        invalidate();//不断绘制
    }

    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mOuterColor = a.getColor(R.styleable.StepView_outerColor,OUTER_COLOR);
        mInnerColor = a.getColor(R.styleable.StepView_innerColor,INNER_COLOR);
        mTextColor = a.getColor(R.styleable.StepView_stepTextColor,TEXT_COLOR);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.StepView_borderWidth,borderWidth);
        mTextSize = a.getDimensionPixelSize(R.styleable.StepView_stepTextSize,mTextSize);
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStrokeCap( Paint.Cap.ROUND);//设置圆角
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mOuterColor);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setStrokeCap( Paint.Cap.ROUND);//设置圆角
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setColor(mInnerColor);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

    }

    private int dp2px(int borderWidth) {
        return 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽和高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(widthMeasureSpec);
        //确保是一个正方形，不一样大取小值
        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画图形
        //1.画外圆，圆弧闭合了，left,right设置0，边缘被裁剪,描边有宽度，需要减去,减去变的宽
        RectF rect = new RectF(mBorderWidth/2,mBorderWidth/2,getWidth()-mBorderWidth/2,getHeight()-mBorderWidth/2);
        //RectF rect = new RectF(0,0,getWidth(),getHeight());//创建一个区域
        mPaint.setColor(mOuterColor);
        canvas.drawArc(rect,135,270,false,mPaint);//起始位置角度135，扫描划过270度
        //2.画内圆
        if(mStepMax==0){//防止除数为0
            return;
        }
        float raudius = (float) mCurrentStep/mStepMax;
        mPaint.setColor(mInnerColor);
        canvas.drawArc(rect,135,270*raudius,false,mPaint);
        //3画文字
        String mStepText = mCurrentStep+"";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(mStepText,0,mStepText.length(),textBounds);//获取字体大小
        Paint.FontMetricsInt metricsInt = mTextPaint.getFontMetricsInt();//获取基线
        int dy = (metricsInt.bottom-metricsInt.top )/2-metricsInt.bottom;//获取dy
        int baseline = getHeight()/2+dy;//计算基线
        int dx = getWidth()/2-textBounds.width()/2;
        canvas.drawText(mStepText,dx,baseline,mTextPaint);
    }
}
