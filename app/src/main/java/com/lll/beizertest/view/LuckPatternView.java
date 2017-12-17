package com.lll.beizertest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longlong on 2017/12/15.
 *
 * @ClassName: LuckPatternView
 * @Description: 九宫格解锁实现思路
 * 1.初始化画笔和格子，以及不同的颜色值，在屏幕中按照屏幕的宽在中心取出一个正方形，然后再这个区域内平分为9个格子，
 *   也就是创建9个点保存在一个3*3的集合中，分别计算出点的位置
 * 2.初始绘制9个格子的内外圆，圆心就是格子的中心，创建一个类保存点的状态，默认的，选中的，错误的以及点的位置数据
 *   这里去屏幕的宽度是正方形的边长
 * 3.处理onTouch 的时候，循环判断是不是点到了九宫格的圆，改变状态，MOVE的之后也判断，把选中的记录在一个选中的集合中。
 *   然后重新绘制
 * 4.绘制链接线，链接线不是重圆心开始的，是重内圆开始，计算出每一个内圆对应的那个点（三角函数），计算两个点的距离，然后绘制Line，起点和终点
 * 5.绘制三角形，通过Path 绘制，在指定的连线的位置。
 * 6.绘制错误的链接线。错误的时候改变点Point的状态为ERROR,然后重新绘制，持续一定时间，清空集合和页面效果。
 * 7.UP的时候回掉密码
 * @Date 2017/12/15
 */

public class LuckPatternView extends View {
    
    public LuckPatternView(Context context) {
        super(context);
    }

    public LuckPatternView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LuckPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
