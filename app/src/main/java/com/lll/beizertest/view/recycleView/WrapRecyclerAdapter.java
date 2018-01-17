package com.lll.beizertest.view.recycleView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longlong on 2018/1/17.
 *
 * @ClassName: WrapRecyclerAdapter
 * @Description: 一个RecycleView 适配器的包装类，方便添加Header和footer，header和footer没有数据和操作
 * @Date 2018/1/17
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter {

    private RecyclerView.Adapter mAdapter;

    /**
     * 用来保存头部
     */
    private SparseArray<View> mHeaders;

    /**
     * 底部
     */
    private SparseArray<View> mFooters ;

    /**
     * head 的标识从 这里开始递增
     */
    private static int BASE_HEAD_KEY = 1000000;

    /**
     * foot 的标识
     */
    private static int BASE_FOOT_KEY = 2000000;

    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        //添加头和尾部View的两个集合 ，SparseArray效率高
        mHeaders = new SparseArray<>();
        mFooters = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //区别头部，尾部，viewType
        Log.e("TAG","mHeaders:"+mHeaders.size());
        if(mHeaders.indexOfKey(viewType)>=0){//查找对应的head
            //头部
            return createHeaderFooterViewHolder(mHeaders.get(viewType));
        }else if(mFooters.indexOfKey(viewType)>=0){
            //底部
            return createHeaderFooterViewHolder(mFooters.get(viewType));
        }
        return mAdapter.onCreateViewHolder(parent,viewType);
    }

    /**
     * 创建头部底部的ViewHolder
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerNums = mHeaders.size();
        if(position<headerNums){
            return;//头部和其他不需要绑定数据
        }

        final int adjPosition = position-headerNums;
        int adapterCount = 0;
        if(mAdapter!=null){
            adapterCount = mAdapter.getItemCount();
            if(adjPosition<adapterCount){
                mAdapter.onBindViewHolder(holder,position);
            }
        }

        //footer不需要处理，不设置数据

    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mHeaders.size() + mFooters.size();
    }

    @Override
    public int getItemViewType(int position) {
        //根据当前的位置设置viewType，区别头部，尾部
        int headers = this.mHeaders.size();
        if (position < headers) {//headView,都是头部
            return mHeaders.keyAt(position);//键值作为标志区别ViewHolder
        }
        final int adjPosition = position - mHeaders.size();
        int adapterCount = mAdapter.getItemCount();
        if (adjPosition < adapterCount) {
            return mAdapter.getItemViewType(adjPosition);
        }

        return mFooters.keyAt(adjPosition - adapterCount);//footView
    }

    public void addHeadView(View view) {
        if (mHeaders.indexOfValue(view) == -1) {
            //集合中没有则添加。
            mHeaders.put(BASE_HEAD_KEY++, view);
        }
        Log.e("TAG","addHeadView:"+BASE_HEAD_KEY);
        notifyDataSetChanged();
    }

    public void addFootView(View view) {
        if (mFooters.indexOfValue(view) == -1) {
            //集合中没有则添加。
            mFooters.put(BASE_FOOT_KEY++, view);
        }
        notifyDataSetChanged();
    }

    public void removeHeadView(View view) {
        if (mHeaders.indexOfValue(view) >= 0) {
            mHeaders.remove(mHeaders.indexOfValue(view));
        }
        notifyDataSetChanged();
    }

    public void removeFootView(View view) {
        if (mFooters.indexOfValue(view) >= 0) {
            mFooters.remove(mFooters.indexOfValue(view));
        }
        notifyDataSetChanged();
    }

}
