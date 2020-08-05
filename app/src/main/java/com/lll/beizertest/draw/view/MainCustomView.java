package com.lll.beizertest.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制View
 *
 * @author RunningDigua
 * @date 2020/4/22
 */
public class MainCustomView extends View {

    private Paint mPaintGreen;

    private Paint mPaintRed;

    private Paint mPathPaint;


    public MainCustomView(Context context) {
        this(context, null);
    }

    public MainCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintGreen = new Paint();
        mPaintRed = new Paint();
        mPaintGreen.setColor(Color.GREEN);
        mPaintGreen.setAntiAlias(true);
        mPaintGreen.setStrokeWidth(5);
        mPaintGreen.setStyle(Paint.Style.FILL);

        mPaintRed.setColor(Color.RED);
        mPaintRed.setAntiAlias(true);
        mPaintRed.setStrokeWidth(5);
        mPaintRed.setStyle(Paint.Style.STROKE);

        //贝塞尔曲线
        mPathPaint = new Paint();
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(5);
        mPathPaint.setAntiAlias(true);
        mPathPaint.setColor(Color.GREEN);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        // 主要的绘制方法
        Rect rect1 = new Rect(200, 10, 400, 100);
        canvas.drawRect(rect1, mPaintRed); //画出原轮廓

        Rect rect2 = new Rect(200, 180, 400, 100);
//        canvas.rotate(50);//顺时针旋转画布
        canvas.skew(1.732f, 0);//矩形斜切60°的tan值
        canvas.drawRect(rect2, mPaintGreen);//画出旋转后的矩形

        drawQuatoLine(canvas);



    }

    /**
     * 绘制贝塞尔曲线
     *  以下的星号表示曲线的作用点 从左到右依次为曲线绘制的点
     *     * 控制点1
     *  *     *     *
     *           * 控制点2
     * @param canvas
     */
    private void drawQuatoLine(Canvas canvas) {
        Path path = new Path();
        path.moveTo(100,300);//第一个点
        path.quadTo(200,200,300,300);// 控制点1和第二个点
        path.quadTo(400,400,500,300);// 控制点2和第三个点

        canvas.drawPath(path,mPathPaint);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // 绘制完成当前主体（执行完onDraw后）执行绘制子View
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // 绘制总控制的方法
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        // 绘制边缘渐变、滑动bar、前景
    }
}

