package com.lll.beizertest.view.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by longlong on 2018/1/5.
 *
 * @ClassName: CircleView
 * @Description: 绘制一个圆
 * @Date 2018/1/5
 */

public class CircleView extends View {

    private int radius = 10;

    private Context context;

    private Paint mPaint;

    private int mColor = Color.RED;

    public int getColor() {
        return mColor;
    }

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint();
        //mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth()/2f;
        float height = getHeight()/2f;
        Log.e("TAG","CircleView-width:"+width);
        Log.e("TAG","CircleView-height:"+height);
        canvas.drawCircle(width,height,width,mPaint);
    }



    public void  changeColor(int color){
        mPaint.setColor(color);
        this.mColor = color;
        invalidate();
    }

}
