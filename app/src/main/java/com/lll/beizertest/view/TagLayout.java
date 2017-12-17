package com.lll.beizertest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlong on 2017/12/8.
 *
 * @ClassName: TagLayout
 * @Description:
 * @Date 2017/12/8
 */

/**
 * 流式布局
 */
public class TagLayout extends ViewGroup {

    private List<List<View>> mChildViews = new ArrayList<>();

    private BaseAdapter mBaseAdapter;

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //什么时候开始换行?? 子View的宽加起来大于屏幕的宽
        // LayoutParams layoutParams = childView.getLayoutParams();//不存在margin,为什么LinearLayout 有？看代码
        mChildViews.clear();//清空，  onMeasure可能会调用多次，刚开始测量，布局变化的时候requestLayout 然后重新绘制
        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingBottom() + getPaddingTop();
        int lineWidth = getPaddingLeft();
        //1.循环测量ziview
        int childCount = getChildCount();
        Log.e("TAG", "childCount:" + childCount);
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            Log.e("TAG", "TagLayout-childHeight2");
            View childView = getChildAt(i);
            if(childView.getVisibility()==View.GONE){
                continue;
            }
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);//会调用子View 的onMeasure方法，之后就有宽高了。
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();
            Log.e("TAG", "TagLayout-childHeight" + ",i:" + i + ",childWidth:" + childWidth + ",childHeight" + childHeight);
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            //系统有MarginLayoutParam ,否则重写ViewGroup 的layoutParam
            if (lineWidth + (childWidth + params.rightMargin + params.leftMargin) > width) {
                height += childHeight + params.topMargin + params.bottomMargin;//换行，增加自己的高度，上一行条目的最大高度
                //height += maxHeight;//换行，增加自己的高度，上一行条目的最大高度
                lineWidth = 0;//换行后
                lineWidth += childWidth + params.rightMargin + params.leftMargin;
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
            } else {//不换行计算宽度
                //不需要换行,把一行的View 添加到集合
                lineWidth += childWidth + params.rightMargin + params.leftMargin;//不换行累加
                maxHeight = Math.max(childView.getMeasuredHeight() + params.topMargin + params.bottomMargin, maxHeight);
            }
            childViews.add(childView);
        }
        height += maxHeight;
        //根据子View计算自己id宽高
        setMeasuredDimension(width, height);

    }

    /**
     * 摆放子view
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("TAG", "onLayout:l:" + l + ",t:" + t + ",r:" + r + ",b:" + b + ",width:" + getResources().getDisplayMetrics().widthPixels + ",height:" + getResources().getDisplayMetrics().heightPixels);
        int left, right, top = getPaddingTop(), bottom;
        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();
            int maxHeight = 0;
            for (View childView : childViews) {
                if(childView.getVisibility()==View.GONE){
                    continue;
                }
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                left += params.leftMargin;
                int childTop = top + params.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = childTop + childView.getMeasuredHeight();
                childView.layout(left, childTop, right, bottom);
                //left 叠加
                left += childView.getMeasuredWidth() + params.rightMargin;

                int childHeight = getMeasuredHeight()+ params.bottomMargin+params.topMargin;
                maxHeight = Math.max(childHeight,maxHeight);
            }
            if (childViews.size() > 0) {
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childViews.get(0).getLayoutParams();
                top += childViews.get(0).getMeasuredHeight() + params.topMargin + params.bottomMargin;//不断计算高度
//                top += maxHeight;//不断计算高度,设置一个最大的高度。
            }
        }
    }

    /**
     * 设置margin
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    /**
     * 添加适配器给TagLayout
     * @param baseAdapter
     */
    public void setAdapter(BaseAdapter baseAdapter){

        if(baseAdapter!=null){
            mBaseAdapter = baseAdapter;
            removeAllViews();
            int count = mBaseAdapter.getCount();
            for(int i=0;i<count;i++){
                View view = mBaseAdapter.getView(i, this);
                addView(view);
            }
        }

    }

}
