package com.lll.beizertest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by longlong on 2017/12/19.
 *
 * @ClassName: MyScrollView
 * @Description:
 * @Date 2017/12/19
 */

public class MyScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener;

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollChangedListener!=null){
            onScrollChangedListener.onScrollChanged(l,t,oldl,oldt);
        }
    }

    /**
     * 滑动监听
     */
    public interface OnScrollChangedListener{
        void  onScrollChanged(int l, int t, int oldl, int oldt);
    }

}
