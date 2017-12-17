package com.lll.beizertest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by longlong on 2017/12/11.
 *
 * @ClassName: TouchViewGroup
 * @Description:
 * @Date 2017/12/11
 */

public class TouchViewGroup extends LinearLayout {

    public TouchViewGroup(Context context) {
        super(context);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TAG","viewGroup-dispatchTouchEvent-2:"+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("TAG","viewGroup-onInterceptTouchEvent-2:"+ev.getAction());
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG","viewGroup-onTouchEvent-2:"+event.getAction());
        return super.onTouchEvent(event);
    }

}
