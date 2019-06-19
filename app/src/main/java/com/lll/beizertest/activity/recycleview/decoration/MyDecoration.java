package com.lll.beizertest.activity.recycleview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by longlong on 2019/6/18.
 *
 * @ClassName: MyDecoration
 * @Description: 实现自定义ItemDecoration
 * @Date 2019/6/18
 */

public class MyDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //绘制Decoration边缘的方法
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        // 最后绘制的方法
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = 1;
        // 获取绘制区域的方法
    }
}
