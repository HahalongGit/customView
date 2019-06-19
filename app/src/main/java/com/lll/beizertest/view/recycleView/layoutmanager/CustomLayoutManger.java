package com.lll.beizertest.view.recycleView.layoutmanager;

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

public class CustomLayoutManger extends RecyclerView.LayoutManager {

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
        //////////////////////////item 复用优化前的代码块//////////////////////
//        int itemCount = getItemCount();//item数量
//        Log.e(TAG, "itemCount:" + itemCount);
//        int offsetY = 0;
//        for (int i = 0; i < itemCount; i++) {
//            Log.e(TAG, "itemCount-i:" + i);
//            View child = recycler.getViewForPosition(i);
//            addView(child);
//            measureChildWithMargins(child, 0, 0);//测量child
//            int width = getDecoratedMeasuredWidth(child);// 获取item+itenDecoration的总宽度
////            child.getMeasuredWidth();// 获取View的测量宽度
//            int height = getDecoratedMeasuredHeight(child);
//            layoutDecorated(child, 0, offsetY, width, offsetY + height);//竖直方向摆放item，Y方向设置位置变化
//            offsetY += height;
//            Log.e(TAG, "offsetY:" + offsetY);
//        }
//        mTotalHeight = Math.max(offsetY, getVetricalSpace());// 如果没有所有的item没有填充满RecycleView就设置为RecycleView的高度
        //RecycleView在LayoutManager中设置RecycleView的item，RecycleView本身不处理子item的布局问题。
        ////////////////////////////////////////复用优化的代码/////////////////////////////////////////////////////

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
        // 如果滑动到最顶
        if (mSumDy + dy < 0) {//下划的时候
            travel = -mSumDy;// travel + mSumDy = 0;
        } else if (mSumDy + dy > mTotalHeight - getVetricalSpace()) {//上划的时候
            // mSumDy+dy 表示滑动的累计距离
            // getVetricalSpace表示真实的RecycleView高度，mTotalHeight - getVetricalSpace()就是上划到底部的距离
            travel = mTotalHeight - getVetricalSpace() - mSumDy;
        }
//        mSumDy += travel; // 相加为0，所以mSumDay  = 0 下次滑动赋值给travel的值也是0，不在滑动
//        offsetChildrenVertical(-travel);// 调用这个方法整体移动。

        ////////////////////////////////////判断回收复用部分/////////////////////////////////////////
        // getItemCound adapter中总共有多少个item
        // getChildCount() 获取当前RecycleView 在显示的item个数
        // getChildAt()获取某个可见位置的View,他的索引不是adapter中的索引，获取第一个可见的是getChildAt(0).获取最后一个可见的是getChildAt(getChildCount()-1)
        // getPosition(View view) 获取某个View在adaper中的索引。结合getChildAt();可以获取到当前屏幕中显示的View在adapter中的索引

        for (int i = getChildCount() - 1; i >= 0; i--) {//当前显示的View
            View child = getChildAt(i);
            if (travel > 0) {//向上滑动 回收当前屏幕上划动越界的View
                if (getDecoratedBottom(child) - travel < 0) {//表示整个View都滑动出去屏幕了
                    removeAndRecycleView(child, recycler);
                    continue;//继续
                }
            } else if (travel < 0) {//向下滑动 最后一个item向下滑动出屏幕后回收
                if (getDecoratedTop(child) - travel > getHeight() - getPaddingBottom()) {
                    //getHeight()-getPaddingBottom() 表示RecycleView可以显示的最低部分
                    removeAndRecycleView(child, recycler);
                    continue;
                }
            }
        }
        Rect visiabeArea = getVisiabeArea(travel);
        if (travel > 0) {
            View lastView = getChildAt(getChildCount() - 1);
            int minPos = getPosition(lastView) + 1;// 滑动前可见的View+1开始判断是否要显示出来了
            for (int i = minPos; i < getItemCount(); i++) {
                Rect rect = mItemRects.get(i);
                if (Rect.intersects(visiabeArea, rect)) {//判断是否在区域内
                    View childView = recycler.getViewForPosition(i);
                    addView(childView);
                    measureChildWithMargins(childView, 0, 0);
                    layoutDecorated(childView, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);
                    //这里layoutDecorated()的时候rect.top - mSumDy,rect.bottom-mSumDy :
                    //需要注意的是，我们的item的位置rect是包含有滚动距离的，而在layout到屏幕上时，屏幕坐标是从(0,0)开始的，
                    // 所以我们需要把高度减去移动距离。需要注意的是，这个移动距离是不包含最新的移动距离travel的，
                    // 虽然我们在判断哪些item是新增的显示的，是假设已经移动了travel，但这只是识别哪些item将要显示出来的策略，
                    // 到目前为止，所有的item并未真正的移动
                    Log.e(TAG, "---Rotation:" + childView.getRotationY());
//                    childView.setRotation(childView.getRotationY() + 1);
                } else {
                    // 不在滑动的区域内
                    break;
                }
            }
        } else {// 向下滑动的时候填充空白
            View firstView = getChildAt(0);
            int maxPos = getPosition(firstView) - 1;// 取屏幕显示的第一个View的上一个View
            for (int i = maxPos; i >= 0; i--) {
                Rect rect = mItemRects.get(i);
                if (Rect.intersects(visiabeArea, rect)) {
                    View childView = recycler.getViewForPosition(i);
                    addView(childView, 0);//在显示区域，插在屏幕显示的第0个位置
                    measureChildWithMargins(childView, 0, 0);
                    layoutDecorated(childView, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);
                    Log.e(TAG, "===Rotation:" + childView.getRotationY());
//                    childView.setRotation(childView.getRotationY() + 1);
                } else {
                    break;
                }
            }
        }

        ///////////////////////////////////////////////
        mSumDy += travel; // 相加为0，所以mSumDay  = 0 下次滑动赋值给travel的值也是0，不在滑动
        Log.e(TAG, "scrollVerticallyBy-mSumDy:" + mSumDy);
        // return super.scrollVerticallyBy(dy, recycler, state);
        // Android中手指向下滑动是负值，向上滑动是正值
//        offsetChildrenVertical(-dy);// 手指上划时子item向上滑动，item的值应该时减去dy
        // 手指上划时子item向上滑动，item的值应该时减去dy
        offsetChildrenVertical(-travel);// 调用View的offsetTopAndBottom()方法来让所有的item滑动一段距离，内部采用循环进行
//        offsetChildrenHorizontal();
        // 调用这个方法整体移动，调用以后才会把新增的显示出来。系统LinearLayoutManager水平垂直滑动采用offsetChildrenVertical()
        // offsetChildrenVertical()方法移动，用于item没有任何操作的移动，如果需要添加一些移动的变化（放大，缩小，旋转等）需要改变策略

        return travel;// dy是每次滚动的距离
    }

    /**
     * 获取滑动后 屏幕可以显示的范围
     * getVetricalSpace()+mSumDy+travel 表示屏幕和滑动后的距离
     *
     * @param travel
     * @return
     */
    private Rect getVisiabeArea(int travel) {
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mSumDy + travel,
                getPaddingRight() + getWidth(), getVetricalSpace() + mSumDy + travel);
        return result;
    }


}
