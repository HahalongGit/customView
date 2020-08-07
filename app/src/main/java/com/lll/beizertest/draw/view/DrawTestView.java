package com.lll.beizertest.draw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lll.beizertest.R;

/**
 * @author RunningDigua
 * @date 2020/7/10
 */
public class DrawTestView extends FrameLayout {

    private static final String TAG = "DrawTestView";

    private Paint mPaint;

    private Bitmap mBitmap;

    public DrawTestView(@NonNull Context context) {
        this(context, null);
    }

    public DrawTestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DrawTestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.GREEN);
        mPaint.setShadowLayer(1,10,10,Color.LTGRAY); // 设置阴影效果
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));//设置发光，内发光
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_img);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "DrawTestView-canvas");

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.e(TAG, "DrawTestView-dispatchDraw");
//        canvas.save(Canvas.ALL_SAVE_FLAG);//对于api 26 接口生效

        canvas.drawColor(Color.RED);
        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.ALL_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100,0,200,100,mPaint);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100,0,200,100,mPaint);
// --------------------------------------------------------------------------------

        // setXfermode 的使用
        int width = 500;
        int height = width * mBitmap.getHeight() / mBitmap.getWidth();
//        mPaint.setColor(Color.RED);
        int layerId = 0;
        if (Build.VERSION.SDK_INT > 21) {
            layerId = canvas.saveLayer(0, 0, width, height, mPaint);
        }
        canvas.drawRoundRect(new RectF(0, 0, width, height), 50, 50, mPaint);//圆角矩形
//        canvas.drawCircle(width/2,height/2,width/2,mPaint);// 圆形
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));// PorterDuff的模式设置SRC_TOP 会让后设置的图片显示和先设置的一样
        canvas.drawBitmap(mBitmap, null, new Rect(0, 0, width, height), mPaint);
//        canvas.drawRect(0,0,width,height,mPaint);
        // 根据PorterDuffXfermode 的 MODE 覆盖上一层图片
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.allow_img1),null,new Rect(0,0,width,height),mPaint);
        canvas.restoreToCount(layerId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
