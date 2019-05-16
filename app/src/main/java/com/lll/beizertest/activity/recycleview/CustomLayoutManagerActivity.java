package com.lll.beizertest.activity.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.lll.beizertest.R;
import com.lll.beizertest.adapter.recycleview.CustomLayoutMnagerAdapter;
import com.lll.beizertest.view.recycleView.itemdecoration.CustomLayoutManger;
import com.lll.beizertest.view.recycleView.itemdecoration.CustomLayoutManger2;
import com.lll.beizertest.view.recycleView.itemdecoration.CustomLayoutManger3;

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

    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_manager);
        ButterKnife.bind(this);
        initData();
        customLayoutMnagerAdapter = new CustomLayoutMnagerAdapter(this, mData);
        recycleCustomManger.setLayoutManager(new CustomLayoutManger2());
        recycleCustomManger.setAdapter(customLayoutMnagerAdapter);
    }

    private void initData() {
        for (int i = 0; i < 300; i++) {
            mData.add("LayoutManger的item-" + i);
        }
    }

}
