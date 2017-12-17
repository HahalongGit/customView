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
        Log.e("TAG","view-dispatchTouchEvent-1:"+event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG","view-onTouchEvent-1:"+event.getAction());
        return true;
    }

    //onClick 在onTouchEvent 的 MOVENT_UP中才执行，在onTouch 的最后执行

}
