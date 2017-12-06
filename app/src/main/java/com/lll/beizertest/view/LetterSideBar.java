package com.lll.beizertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/11/30.
 *
 * @ClassName: LetterSideBar
 * @Description:
 * @Date 2017/11/30
 */

public class LetterSideBar extends View {

    private Paint mPaint;

    private String currentLetter;

    /**
     * 是否按下，或者滑动
     */
    private boolean isTouch = false;

    private String letters[] = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
    };

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setTextSize(px2sp(16));
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    private int px2sp(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (int) mPaint.measureText("A");
        int measureWidth = width + getPaddingLeft() + getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isTouch){
            mPaint.setColor(Color.parseColor("#DDDDDD"));
        }else {
            mPaint.setColor(Color.parseColor("#ffffff"));
        }
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

        int letterHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        for (int i = 0; i < letters.length; i++) {
            int width = (int) mPaint.measureText(letters[i]);
            int x = getWidth() / 2 - width / 2;
            int letterCenter = i * letterHeight + letterHeight / 2 + getPaddingTop();//每个字母的中心
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;//dy
            int baseline = letterCenter + dy;
            if(letters[i].equals(currentLetter) && isTouch){
                mPaint.setColor(Color.BLUE);
            }else {
                mPaint.setColor(Color.parseColor("#555555"));
            }
            canvas.drawText(letters[i], x, baseline, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                isTouch = true;
                int letterHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
                float touchY = event.getY();
                Log.e("TAG","touchY:"+touchY);
                Log.e("TAG","letterNumber:"+currentLetter);
                int letterNumber = (int) (touchY / letterHeight);
                if (letterNumber < 0) {
                    letterNumber = 0;
                }
                if (letterNumber > letters.length-1) {
                    letterNumber = letters.length-1;
                }
                currentLetter = letters[letterNumber];
                break;
            }
            case MotionEvent.ACTION_UP: {
                isTouch = false;
                break;
            }
        }
        invalidate();
        return true;
    }



}
