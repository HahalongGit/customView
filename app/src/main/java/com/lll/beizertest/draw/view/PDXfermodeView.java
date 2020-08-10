package com.lll.beizertest.draw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lll.beizertest.R;

/**
 * Xfermode使用练习
 *
 * @author RunningDigua
 * @date 2020/8/7
 */
public class PDXfermodeView extends View {

    private static final String TAG = "PDXfermodeView";

    private Paint mPaint;

    private Bitmap mBitmap;

    private PorterDuff.Mode mMode;

    public PDXfermodeView(Context context) {
        this(context, null);
    }

    public PDXfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PDXfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_img);
        mPaint.setColor(Color.GREEN);

        // 默认模式
        mMode = PorterDuff.Mode.SRC_IN;


    }

    public void setXfermode(PorterDuff.Mode mode) {
        mMode = mode;
        postInvalidate();
    }

    public Xfermode getXfermode() {
        return mPaint.getXfermode();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw-mMode:" + mMode);
        int width = 400;
        int height = width * mBitmap.getHeight() / mBitmap.getWidth();
        int layertId = 0;
        if (Build.VERSION.SDK_INT > 21) {
            layertId = canvas.saveLayer(new RectF(0, 0, width, height), mPaint);
        }
        canvas.drawRoundRect(new RectF(0, 0, width, height), 50, 50, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(mMode));
        canvas.drawBitmap(mBitmap, null, new RectF(0, 0, width, height), mPaint);
        Log.e(TAG, "onDraw-after-mMode:" + mPaint.getXfermode().toString());
        mPaint.setXfermode(null);
        canvas.restoreToCount(layertId);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        //实际大小
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        Log.e(TAG, "onLayout-mBitmap-width:" + width);
        Log.e(TAG, "onLayout-mBitmap-height:" + height);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? measureWidth : width,
                (heightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout-width:" + getMeasuredWidth());
        Log.e(TAG, "onLayout-height:" + getMeasuredHeight());
    }
}
