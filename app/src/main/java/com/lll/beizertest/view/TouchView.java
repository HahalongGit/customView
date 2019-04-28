package com.lll.beizertest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by longlong on 2017/12/10.
 *
 * @ClassName: TouchView
 * @Description:
 * @Date 2017/12/10
 */

public class TouchView extends View {
    private static final String TAG = "TouchView";
    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "view-test-dispatchTouchEvent-11:" + event.getAction());
        Log.e(TAG, "view-test-dispatchTouchEvent-22:" + super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "view-test-onTouchEvent-1:" + event.getAction());
        Log.e(TAG, "view-test-onTouchEvent-2:" + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    //onClick 在onTouchEvent 的 MOVENT_UP中才执行，在onTouch 的最后执行

}
