package com.lll.beizertest.adapter.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longlong on 2019/5/14.
 *
 * @ClassName: CustomLayoutMnagerAdapter
 * @Description: 自定义RecycleView LayoutManager 适配器
 * @Date 2019/5/14
 */

public class CustomLayoutMnagerAdapter extends RecyclerView.Adapter {

    private static final String TAG = "CustomLayoutMnagerAdapt";

    private Context context;

    public CustomLayoutMnagerAdapter(Context context) {
        this.context = context;
    }

    private int mCreateViewHodler;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder viewHolder = new ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycle_custom_layout_manager_adapter_layout, parent, false)
        );
        mCreateViewHodler++;
        Log.e(TAG, "onCreateViewHolder" + mCreateViewHodler);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        Log.e(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    /**
     *
     */
    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemContent)
        TextView tvItemContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
