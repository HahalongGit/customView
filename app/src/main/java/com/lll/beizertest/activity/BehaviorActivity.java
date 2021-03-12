package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lll.beizertest.R;

/**
 * CoordinatorLayout与Behavior 使用和自定义，实现一些页面的滑动联动，列表向下，头部的一个View会消失或者显示出来
 * 实例：https://github.com/chrisbanes/cheesesquare
 */
public class BehaviorActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recycleBehaver;

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        toolbar = findViewById(R.id.tb_behavior);
        recycleBehaver = findViewById(R.id.recycle_behaver);
        layoutManager = new LinearLayoutManager(this);
        recycleBehaver.setLayoutManager(layoutManager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recycleBehaver.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                BehaviorViewHolder viewHolder = new BehaviorViewHolder(
                        LayoutInflater.from(BehaviorActivity.this).inflate(R.layout.recycle_behavior_adapter_item, parent, false));
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
    }


    public static class BehaviorViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public BehaviorViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_listTitle);
        }
    }

}
