package com.lll.beizertest.view.recycleView.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by longlong on 2019/5/14.
 *
 * @ClassName: CustomLayoutManger
 * @Description: 自定义LayoutManger步骤：
 * 1.重写generateDefaultLayoutParams()设置每个item的margin、padding等
 * 2.重写onLayoutChildren();添加从recycle获取itemView到RecycleView中，也就是实现ViewGroup的layout()作用
 * 3.设置滑动
 * 4.设置回收复用的方法
 * detachAndScrapAttachedViews(recycler);在onLyoutChildren()中剥离
 * removeAndRecycleView();在scrollVerticalBy或者scrollHorizonzalBy 滑出屏幕后回收item的方法
 * recycle.getViewForPosition();向RecycleView申请获取一个item,至于是从那个集合中取到的（复用的集合和保存的重新布局的集合），我们不知道
 * 5.根据滑动的距离值正负判断向上还是向下的滑动，
 * <p>
 * https://blog.csdn.net/harvic880925/article/details/84866486
 * @Date 2019/5/14
 */

public class CustomLayoutManger2 extends RecyclerView.LayoutManager {

    private static final String TAG = "CustomLayoutManger";

    private int mTotalHeight;

    private SparseArray<Rect> mItemRects = new SparseArray<>();

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        // 每个item的LayoutParam,如果需要设置item的margin、padding等可以在这里设置，默认情况下我们不需要修改
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        int itemCount = getItemCount();
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        detachAndScrapAttachedViews(recycler);
        // 1.使用detachAndScrapAttachedVies();剥离item
        // 2.一屏幕能放下多少个item就创建多少个item，不要多创建
        View childView = recycler.getViewForPosition(0);// 获取一个View用来获取长宽等数据
        measureChildWithMargins(childView, 0, 0);
        int mItemWidth = getDecoratedMeasuredWidth(childView);
        int mItemHeight = getDecoratedMeasuredHeight(childView);
        int visiableCount = getVetricalSpace() / mItemHeight;// 一个屏幕可以显示的item

        int offsetsY = 0;//竖直方向的偏移量
        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect(0, offsetsY, mItemWidth, offsetsY + mItemHeight);
            mItemRects.put(i, rect);
            offsetsY += mItemHeight;
        }

        for (int i = 0; i < visiableCount; i++) {
            Rect rect = mItemRects.get(i);
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);//measure()
            layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom);//layout()
            // 对应于自定义View中的measure()和layout();
        }
        // RecycleView 填充后的高度如果不满一屏幕就设置RecycleView为整个屏幕的高
        mTotalHeight = Math.max(offsetsY, getVetricalSpace());
    }

    /**
     * @return
     */
    private int getVetricalSpace() {
        Log.e(TAG, "getHeight:" + getHeight());//getHeight()是一个定值
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    @Override
    public boolean canScrollVertically() {
        // 设置是否垂直方向滑动
        return true;
    }

    private int mSumDy = 0;

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.e(TAG, "scrollVerticallyBy-dy:" + dy);// 上划是正值，下滑是负值
        int travel = dy;
        if (mSumDy + dy < 0) {
            travel = -mSumDy;
        } else if (mSumDy + dy > mTotalHeight - getVetricalSpace()) {//上划的时候
            travel = mTotalHeight - getVetricalSpace() - mSumDy;
        }
        mSumDy += travel;
        Rect visiabeArea = getVisiabeArea();

        for (int i = getChildCount() - 1; i >= 0; i--) {//当前显示的View
            View child = getChildAt(i);
            if (travel > 0) {//向上滑动 回收当前屏幕上划动越界的View
                if (getDecoratedBottom(child) - travel < 0) {//表示整个View都滑动出去屏幕了
                    removeAndRecycleView(child, recycler);
                    continue;
                }
            } else if (travel < 0) {//向下滑动 最后一个item向下滑动出屏幕后回收
                if (getDecoratedTop(child) - travel > getHeight() - getPaddingBottom()) {
                    //getHeight()-getPaddingBottom() 表示RecycleView可以显示的最低部分
                    removeAndRecycleView(child, recycler);
                    continue;
                }
            }
        }

        View lastView = getChildAt(getChildCount() - 1);
        View firstView = getChildAt(0);
        // detachAndScrapAttachedViews 以后获取的view是null,提前获取firstView 和 lastView
//        detachAndScrapAttachedViews(recycler); 文章说使用这个方法来回收数据，没有用到


        if (travel > 0) {
            int minPos = getPosition(lastView);// int minPos = getPosition(lastView)  + 1 滑动前可见的View+1开始判断是否要显示出来了
            for (int i = minPos; i < getItemCount(); i++) {
                insertView(recycler, visiabeArea, i, false);
            }
        } else {// 向下滑动的时候填充空白
            int maxPos = getPosition(firstView);// int maxPos = getPosition(firstView) - 1 取屏幕显示的第一个View的上一个View
            for (int i = maxPos; i >= 0; i--) {
                insertView(recycler, visiabeArea, i, true);
            }
        }


        return travel;// dy是每次滚动的距离
    }

    private void insertView(RecyclerView.Recycler recycler, Rect visiabeArea, int i, boolean is) {
        Rect rect = mItemRects.get(i);
        if (Rect.intersects(visiabeArea, rect)) {//判断是否在区域内
            View childView = recycler.getViewForPosition(i);
            if (is) {
                addView(childView, 0);
            } else {
                addView(childView);
            }
            measureChildWithMargins(childView, 0, 0);
            layoutDecorated(childView, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);
            childView.setRotation(childView.getRotationY() + 1);
        }
    }

    /**
     * 获取滑动后 屏幕可以显示的范围
     * getVetricalSpace()+mSumDy+travel 表示屏幕和滑动后的距离
     *
     * @param
     * @return
     */
    private Rect getVisiabeArea() {//int travel
//        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mSumDy + travel,
//                getPaddingRight() + getWidth(), getVetricalSpace() + mSumDy + travel);
        // 前面已经累加了移动的距离travel
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mSumDy,
                getPaddingRight() + getWidth(), getVetricalSpace() + mSumDy);
        return result;
    }


}
