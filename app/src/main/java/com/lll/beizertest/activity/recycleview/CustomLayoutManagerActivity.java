package com.lll.beizertest.activity.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lll.beizertest.R;
import com.lll.beizertest.activity.recycleview.decoration.MyDecoration;
import com.lll.beizertest.adapter.recycleview.CustomLayoutMnagerAdapter;
import com.lll.beizertest.view.recycleView.layoutmanager.CustomLayoutManger2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义RecycleView LayoutManger复习
 */
public class CustomLayoutManagerActivity extends AppCompatActivity {


    @BindView(R.id.recycle_customManger)
    RecyclerView recycleCustomManger;

    private CustomLayoutMnagerAdapter customLayoutMnagerAdapter;

    /**
     * 系统定义的分割线
     */
//    private DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);//华丽的分割线

    private List<String> mData = new ArrayList<>();

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_manager);
        ButterKnife.bind(this);
        initData();
        customLayoutMnagerAdapter = new CustomLayoutMnagerAdapter(this, mData);
        recycleCustomManger.setLayoutManager(new CustomLayoutManger2());
        recycleCustomManger.setAdapter(customLayoutMnagerAdapter);
        recycleCustomManger.addItemDecoration(new MyDecoration());
    }

    private void initData() {
        for (int i = 0; i < 300; i++) {
            mData.add("LayoutManger的item-" + i);
        }
    }

}
