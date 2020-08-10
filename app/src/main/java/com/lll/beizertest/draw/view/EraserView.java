package com.lll.beizertest.draw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

import java.io.InputStream;

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

        //dog_img 放在drawable-xxhdpx
        mImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_img);
        Log.e(TAG, "mImageBitmap-width:" + mImageBitmap.getWidth());

        // 创建和图片一样大小的空白透明bitmap
        mBmpDST = Bitmap.createBitmap(mImageBitmap.getWidth(), mImageBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mPaint.setStrokeWidth(30);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制文字图层
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img_award);
        canvas.drawBitmap(bitmap,0,0,mPaint);//从0点开始绘制

        int layertId = 0;
        if (Build.VERSION.SDK_INT > 21) {
            // 创建和图片一样大小的图层
            layertId = canvas.saveLayer(0, 0, mImageBitmap.getWidth(), mImageBitmap.getHeight(), mPaint);//设置图层的大小
        }
        Canvas canvas1 = new Canvas(mBmpDST);
        canvas1.drawPath(mPath, mPaint);// 在空白bitmap上回值轨迹
        canvas.drawBitmap(mBmpDST, 0, 0, mPaint);

        //Mode.SRC_OUT简单来说，当目标图像有图像时结果显示空白像素，当目标图像没有图像时，结果显示源图像。
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = mImageBitmap.getWidth();
        int height = mImageBitmap.getHeight();

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthMeasureSize : width,
                (heightMode == MeasureSpec.EXACTLY) ? heightMeasureSize : height);
    }
}
