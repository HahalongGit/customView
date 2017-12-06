package com.lll.beizertest.view;

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
 * Created by longlong on 2017/11/29.
 *
 * @ClassName: ShapeView
 * @Description:
 * @Date 2017/11/29
 */

public class ShapeView extends View {

    /**
     * 默认形状
     */
    private SHAPE currnetShape = SHAPE.Circle;

    private Paint mPaint;

    private Path path;

    public ShapeView(Context context) {
        this(context,null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(currnetShape == SHAPE.Square){
            mPaint.setColor(Color.BLUE);
            Rect rect = new Rect(0,0,getWidth(),getHeight());
            canvas.drawRect(rect,mPaint);
        }else if(currnetShape == SHAPE.Triangle){
            //画三角形
            mPaint.setColor(Color.RED);
            if(path==null){
                path = new Path();
            }
            //画曲线 path.quadTo();
            path.moveTo(getWidth()/2,0);
            path.lineTo(getWidth(), (float) (getHeight()/2*Math.sqrt(3)));
            path.lineTo(0,(float) (getHeight()/2*Math.sqrt(3)));//等边三角形
            path.close();
            canvas.drawPath(path,mPaint);
        }else if(currnetShape ==SHAPE.Circle){
            mPaint.setColor(Color.YELLOW);
            canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2,mPaint);
        }
        //画正方形

        //画三角形

        //画原型
    }

    public void exChange(){
        switch (currnetShape){
            case Circle:{
                currnetShape = SHAPE.Square;
                break;
            }
            case Square:{
                currnetShape = SHAPE.Triangle;
                break;
            }
            case Triangle:{
                currnetShape = SHAPE.Circle;
                break;
            }
        }
        invalidate();
    }

    public enum SHAPE{
        Circle,Square,Triangle
    }

}
