package com.lll.beizertest.view.recycleView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longlong on 2018/1/17.
 *
 * @ClassName: WrapRecyclerView
 * @Description: 直接拦截setAdapter设置Wrap Adapter
 *                 这种包裹的Adapter 在Adpter调用删除方法的时候不会更新，WrapRecyclerAdapter 显示不会更新。需要用观察者模式修改，
 * @Date 2018/1/17
 */

public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerAdapter mAdapter;

    /**
     * 不同的删除对应的回调实现 观察者
     */
    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mAdapter.notifyDataSetChanged();//通知跟新
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            mAdapter.notifyItemChanged(positionStart,itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            mAdapter.notifyItemRangeChanged(positionStart,itemCount,payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            mAdapter.notifyItemRangeInserted(positionStart,itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            mAdapter.notifyItemRangeRemoved(positionStart,itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            mAdapter.notifyItemMoved(fromPosition,toPosition);//itemCount 不处理 ，源码是1
        }
    };


    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        //拦截设置适配：修改
        if (adapter instanceof WrapRecyclerAdapter) {
            mAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mAdapter = new WrapRecyclerAdapter(adapter);
            //列表的数据改变了。适配器的没有改变，需要一个东西通知改变。观察者模式
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
        super.setAdapter(mAdapter);
    }

    public void addHeadView(View view) {
        if (mAdapter != null) {
            mAdapter.addHeadView(view);
        }
    }

    public void addFootView(View view) {
        if (mAdapter != null) {
            mAdapter.addFootView(view);
        }
    }

    public void removeHeadView(View view) {
        if (mAdapter != null) {
            mAdapter.removeHeadView(view);
        }
    }

    public void removeFootView(View view) {
        if (mAdapter != null) {
            mAdapter.removeFootView(view);
        }
    }


}
