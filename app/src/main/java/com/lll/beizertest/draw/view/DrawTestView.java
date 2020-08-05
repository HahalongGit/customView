package com.lll.beizertest.draw.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 *
 *
 * @author RunningDigua
 * @date 2020/7/10
 */
public class DrawTestView extends FrameLayout {

    private static final String TAG = "DrawTestView";

    private Paint mPaint;

    public DrawTestView(@NonNull Context context) {
        this(context,null);
    }

    public DrawTestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public DrawTestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint.setColor(Color.GREEN);
//        mPaint.setShadowLayer(1,10,10,Color.LTGRAY); // 设置阴影效果
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));//设置发光，内发光
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG,"DrawTestView-canvas");

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.e(TAG,"DrawTestView-dispatchDraw");
//        canvas.save(Canvas.ALL_SAVE_FLAG);//对于api 26 接口生效

        canvas.drawColor(Color.RED);
        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.ALL_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100,0,200,100,mPaint);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100,0,200,100,mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {

        return super.generateLayoutParams(lp);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }
}
