package com.lll.beizertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lll.beizertest.R;
import com.lll.beizertest.view.nestedscrolling.StickyNavLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * NestedScrolling 机制
 * 参考 鸿洋 https://blog.csdn.net/lmj623565791/article/details/52204039
 * <p>
 * #ViewGroup 中 addViewInLayout
 */
public class NestedScrollingActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.recycle_list)
    RecyclerView mRecycleList;
    @BindView(R.id.stickyNavLayout)
    StickyNavLayout mStickyNavLayout;

    private List<String> mStringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scrolling);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mStringList.add("java编程思想");
        mStringList.add("数据结构与算法");
        mStringList.add("android底层设计");
        mStringList.add("设计模式");
        mStringList.add("java从入门到放弃1");
        mStringList.add("java从入门到放弃2");
        mStringList.add("java从入门到放弃3");
        mStringList.add("java从入门到放弃4");
        mStringList.add("java从入门到放弃5");
        mStringList.add("java从入门到放弃6");
        mStringList.add("java从入门到放弃7");
        mStringList.add("java从入门到放弃8");
        mStringList.add("java从入门到放弃9");
        mStringList.add("java从入门到放弃10");
        mStringList.add("java从入门到放弃11");
        mStringList.add("java从入门到放弃12");
        mStringList.add("java从入门到放弃13");
        mStringList.add("java从入门到放弃14");
        mStringList.add("java从入门到放弃15");
        mStringList.add("java从入门到放弃16");
        mStringList.add("java从入门到放弃17");
        mStringList.add("java从入门到放弃18");
        mStringList.add("java从入门到放弃19");
        mStringList.add("java从入门到放弃20");
        mStringList.add("java从入门到放弃10");
        mStringList.add("java从入门到放弃10");
        mStringList.add("java从入门到放弃10");
        mStringList.add("java从入门到放弃10");
        mStringList.add("java从入门到放弃10");
        mStringList.add("java从入门到放弃10");
        mStringList.add("java从入门到放弃10");

        NestedScrollingAdapter nestedScrollingAdapter = new NestedScrollingAdapter(mStringList);
        mRecycleList.setAdapter(nestedScrollingAdapter);

    }

    private class NestedScrollingAdapter extends RecyclerView.Adapter {

        private List<String> mList;

        public NestedScrollingAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NestedScrollingViewHolder viewHolder = new NestedScrollingViewHolder(
                    LayoutInflater.from(NestedScrollingActivity.this)
                            .inflate(R.layout.recycle_item_nestedscrlling_demo, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            NestedScrollingViewHolder viewHolder = (NestedScrollingViewHolder) holder;
            viewHolder.tvItemContent.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    protected class NestedScrollingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_itemContent)
        TextView tvItemContent;

        public NestedScrollingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
