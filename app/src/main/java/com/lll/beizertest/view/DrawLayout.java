package com.lll.beizertest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2017/12/11.
 *
 * @ClassName: DrawLayout
 * @Description:
 * @Date 2017/12/11
 */

/**
 * 侧滑实现
 */
public class DrawLayout extends HorizontalScrollView {

    private int mMenuWidth =  50;
    //private DrawerLayout drawerLayout;

    public DrawLayout(Context context) {
        this(context,null);
    }

    public DrawLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawLayout);
        mMenuWidth = (int) a.getDimension(R.styleable.DrawLayout_menuWidth,mMenuWidth);
    }


    /**
     * 布局加载完毕再回回调的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //指定content的宽度是内容的宽度
        //制定menu的宽度是muenu减去属性的宽度
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //滑动回调，获取滑动距离，设置缩放等滑动的时候的动画。
        float scale = 1f*l/mMenuWidth;//scale 的值 1-- 0
        float rightScale = 0.7f +0.3f*scale;//scale 的值 1-- 0
        Log.e("TAG","onScrollChanged");
    }
}
