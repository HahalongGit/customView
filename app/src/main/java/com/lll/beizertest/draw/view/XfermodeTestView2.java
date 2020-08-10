package com.lll.beizertest.draw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO:describe what the class or interface does.
 *
 * @author RunningDigua
 * @date 2020/8/7
 */
public class XfermodeTestView2 extends View {
    private static final String TAG = "XfermodeTestView";
    private Paint mPaint;

    private PorterDuff.Mode mMode;

    private int mWidth = 300;

    private int mHeight = 300;

    private Bitmap mDstBitmap;

    private Bitmap mRscBitmap;

    public void setXfermode(PorterDuff.Mode mode) {
        mMode = mode;
        postInvalidate();
    }

    public XfermodeTestView2(Context context) {
        this(context, null);
    }

    public XfermodeTestView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeTestView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        // 默认模式
        mMode = PorterDuff.Mode.SRC_IN;

        mDstBitmap = drawDstBitmap(mWidth, mHeight);
        mRscBitmap = drawSrcBitmap(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw----");

        int layertId = 0;
        if (Build.VERSION.SDK_INT > 21) {
            layertId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint);//设置图层的大小
        }

        canvas.drawBitmap(mRscBitmap, mWidth/2, mWidth/2, mPaint);//方 起点在0,0
        mPaint.setXfermode(new PorterDuffXfermode(mMode));
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);// 圆  50,50 是绘制的起点
        mPaint.setXfermode(null);

        canvas.restoreToCount(layertId);
    }

    private Bitmap drawDstBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建空白Bitmap
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(0xFFFFCC44);//在空白bitmap上绘制
        canvas.drawOval(new RectF(0, 0, width, height), paint);
        return bitmap;
    }

    private Bitmap drawSrcBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(0xFF66AAFF);
        canvas.drawRect(0, 0, width, height, paint);
        return bitmap;
    }

}
