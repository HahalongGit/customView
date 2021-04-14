package com.lll.beizertest.activity.recycleview.adater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lll.beizertest.databinding.RecycleDrawItemLayoutBinding;
import com.lll.beizertest.view.TextView;

import java.util.List;

/**
 * TODO:describe what the class or interface does.
 *
 * @author RunningDigua
 * @date 2021/4/14
 */
public class DrawRecycleViewAdapter extends RecyclerView.Adapter<DrawRecycleViewAdapter.DrawItemViewHolder> {

    private static final String TAG = "DrawRecycleViewAdapter";

    private Context mContext;

    private List<String> list;

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public DrawRecycleViewAdapter(Context context, List<String> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DrawItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecycleDrawItemLayoutBinding binding = RecycleDrawItemLayoutBinding.inflate(LayoutInflater.from(mContext), viewGroup, false);
        return new DrawItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final DrawItemViewHolder drawItemViewHolder, final int i) {
        drawItemViewHolder.mBinding.tvDragText.setText("我是一个item---" + list.get(i));
        drawItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "onLongClick--" + i);
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(i, drawItemViewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "ItemCount--size:" + list.size());
        return list.size();
    }

    protected class DrawItemViewHolder extends RecyclerView.ViewHolder {

        RecycleDrawItemLayoutBinding mBinding;

        public DrawItemViewHolder(@NonNull RecycleDrawItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public interface OnItemLongClickListener {

        void onItemLongClick(int position, RecyclerView.ViewHolder holder);

    }

}
