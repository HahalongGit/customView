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
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.lll.beizertest.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by longlong on 2017/11/29.
 *
 * @ClassName: ProgressBar
 * @Description: 定义一个原型进度条
 * @Date 2017/11/29
 */

public class ZHProgressBar extends View {

    /**
     * 外层圆颜色
     */
    private int mOuterBackgroundColor = Color.BLUE;

    /**
     * 内层进度颜色
     */
    private int mInnerBackgroundColor = Color.RED;

    /**
     * 圆弧边的粗细
     */
    private int mProgressBorderWidth = 10;

    /**
     * 进度文字的大小
     */
    private int mProgressTextSize = 18;

    /**
     * 字体颜色
     */
    private int mProgressTextColor = Color.RED;

    private Paint mOuterPaint, mInnerPaint, mTextPaint;

    /**
     * 当前进度
     */
    private int currentProgress;

    /**
     * 最大进度
     */
    private int maxProgress;

    /**
     * 最小的半径
     */
    private int minWidth = 100;

    public ZHProgressBar(Context context) {
        this(context, null);
    }

    public ZHProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 当前进度
     *
     * @param currentProgress
     */
    public synchronized void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }

    /**
     * 总进度
     *
     * @param maxProgress
     */
    public synchronized void setMaxProgress(int maxProgress) {
        if (maxProgress < 0 || maxProgress < currentProgress) {
            new IllegalArgumentException("？？？，怎么回事");
        }
        this.maxProgress = maxProgress;
    }

    public ZHProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mProgressTextSize = a.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, sp2px(18));//获取字的大小
        mOuterBackgroundColor = a.getColor(R.styleable.ProgressBar_outerBackgroundColor, Color.BLUE);
        mInnerBackgroundColor = a.getColor(R.styleable.ProgressBar_innerBackgroundColor, Color.RED);
        mProgressBorderWidth = (int) a.getDimension(R.styleable.ProgressBar_progressBorderWidth, dp2px(10));
        mProgressTextColor = a.getColor(R.styleable.ProgressBar_progressTextColor, Color.RED);
        a.recycle();

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBackgroundColor);
        mOuterPaint.setStrokeWidth(mProgressBorderWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBackgroundColor);
        mInnerPaint.setStrokeWidth(mProgressBorderWidth);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        Log.e("TAG", "mProgressTextSize:" + mProgressTextSize);
        mTextPaint.setTextSize(mProgressTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //保证正方形
        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            width = height = dp2px(minWidth);
            setMeasuredDimension(width, height);
            //设置为wrap_content 的时候默认的大小是100dp的大小
        } else {// MeasureSpec.EXACTLY 固定大小，或者math_parent
            setMeasuredDimension(width > height ? height : width, width > height ? height : width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //圆形点
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - mProgressBorderWidth / 2, mOuterPaint);
        if (maxProgress == 0) {
            return;
        }
        float rade = (float) currentProgress / maxProgress;
        RectF rectF = new RectF(mProgressBorderWidth / 2, mProgressBorderWidth / 2, getWidth() - mProgressBorderWidth / 2, getWidth() - mProgressBorderWidth / 2);
        canvas.drawArc(rectF, 270, rade * 360, false, mInnerPaint);

//        NumberFormat numberFormat = new DecimalFormat("0.00");
//        String format = numberFormat.format(rade * 100);
//        String text = format + "%";
//        Rect bounds = new Rect();
//        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
//        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
//        int dx = getWidth() / 2 - bounds.width() / 2;
//        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
//        int baseline = getHeight() / 2 + dy;
//        canvas.drawText(text, dx, baseline, mTextPaint);
//
//        //绘制一个水平进度条
//        canvas.drawLine(0, getHeight() / 2, getWidth() - mProgressBorderWidth / 2, getHeight() / 2, mOuterPaint);
//        int myWidth = (int) (rade * (getWidth() - mProgressBorderWidth / 2));
//        canvas.drawLine(0, getHeight() / 2, myWidth, getHeight() / 2, mInnerPaint);

    }

    private int dp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private int sp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip, getResources().getDisplayMetrics());
    }
}
