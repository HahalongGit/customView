package com.lll.beizertest.draw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lll.beizertest.R;

/**
 * 使用XFermode 的 Mode.Src_OUT实现橡皮差功能。（刮刮乐功能）
 *
 * @author RunningDigua
 * @date 2020/8/7
 */
public class EraserView extends View {
    private static final String TAG = "XfermodeTestView";
    private Paint mPaint;

    private PorterDuff.Mode mMode;

    private int mWidth = 400;

    private int mHeight = 400;

    private Bitmap mDstBitmap;

    private Bitmap mRscBitmap;

    private Bitmap mBmpDST;

    private Bitmap mImageBitmap;

    private Path mPath = new Path();

    public void setXfermode(PorterDuff.Mode mode) {
        mMode = mode;
        postInvalidate();
    }

    public EraserView(Context context) {
        this(context, null);
    }

    public EraserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EraserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 实现橡皮擦效果获取图片 和bitmap
        mImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_img);
        // 创建空白透明bitmap
        mBmpDST = Bitmap.createBitmap(mImageBitmap.getWidth(), mImageBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mPaint.setStrokeWidth(30);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layertId = 0;
        if (Build.VERSION.SDK_INT > 21) {
            layertId = canvas.saveLayer(0, 0, mBmpDST.getWidth(), mBmpDST.getHeight(), mPaint);//设置图层的大小
        }

        Canvas canvas1 = new Canvas(mBmpDST);
        canvas1.drawPath(mPath, mPaint);// 在空白bitmap上回值轨迹
        canvas.drawBitmap(mBmpDST, 0, 0, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mImageBitmap, 0, 0, mPaint);//起点 0，0
        mPaint.setXfermode(null);

        canvas.restoreToCount(layertId);
    }



    private float mPreX;

    private float mPreY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPath.moveTo(event.getX(), event.getY());
            mPreX = event.getX();
            mPreY = event.getY();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float mControlX = (mPreX + event.getX()) / 2;// 贝塞尔曲线控制点（两个点的中点）
            float mControlY = (mPreY + event.getY()) / 2;
            mPath.quadTo(mPreX, mPreY, mControlX, mControlY);
            Log.e(TAG, "onTouchEvent-x:" + event.getX());
            mPreX = event.getX();
            mPreY = event.getY();
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}
