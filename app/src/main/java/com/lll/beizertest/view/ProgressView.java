package com.lll.beizertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by longlong on 2017/9/12.
 *
 * @ClassName: ProgressView
 * @Description: 矿宝宝设置的物流进度View
 * @Date 2017/9/12
 */

public class ProgressView extends View {

    private String[] attrs = new String[]{"确认抢单", "待提货", "运输中", "已完成"};

    private Paint mPaint = new Paint();

    private Rect mRect = new Rect();

    private int progress = 1;

    private int progressColor = Color.BLUE;

    private Context mContext;


    /**
     * 设置进度的颜色
     * @param progressColor
     */
    public void setProgressColor(@ColorInt int progressColor) {
        this.progressColor = progressColor;
        mPaint.setColor(progressColor);
        invalidate();//修改颜色后可以直接改变，重新绘制
    }

    /**
     * 设置进度默认值 1
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        //invalidate();
    }


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setMinimumHeight(dp2px(context, 50));
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(dp2px(context, 14));
        Log.e("TAG","ProgressView");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("TAG","ProgressView-onDraw");
        int size = attrs.length;
        int centerHight = getMeasuredHeight() / 2;
        int textLine = centerHight - dp2px(mContext, 10);
        int progressLine = centerHight + dp2px(mContext, 10);
        int mWidth = getMeasuredWidth() / (size * 2);
        for (int i = 1; i < size * 2; i += 2) {
            int r = dp2px(mContext, 5);
            if (i <= progress * 2 + 1) {
                mPaint.setColor(progressColor);
            } else {
                mPaint.setColor(Color.GRAY);
            }
            if (i >= 3) {
                mPaint.setStrokeWidth(dp2px(mContext,1));
                canvas.drawLine((i - 2) * mWidth + 2 * r, progressLine, i * mWidth - 2 * r, progressLine, mPaint);
            }
            canvas.drawCircle(i * mWidth, progressLine, r, mPaint);
            String str = attrs[(i - 1) / 2];
            mPaint.getTextBounds(str, 0, str.length(), mRect);
            Paint.FontMetricsInt metricsInt = mPaint.getFontMetricsInt();

            int baseline = textLine - metricsInt.bottom / 2 - metricsInt.top / 2;//计算出一个baseLine,计算有问题
            canvas.drawText(str, i * mWidth - mRect.width() / 2,baseline, mPaint);

        }
    }


    /**
     * dp 到 px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * px 到 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

}
