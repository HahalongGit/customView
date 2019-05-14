package com.lll.beizertest.activity.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.lll.beizertest.R;
import com.lll.beizertest.adapter.recycleview.CustomLayoutMnagerAdapter;
import com.lll.beizertest.view.recycleView.itemdecoration.CustomLayoutManger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义RecycleView LayoutManger复习
 */
public class CustomLayoutManagerActivity extends AppCompatActivity {


    @BindView(R.id.recycle_customManger)
    RecyclerView recycleCustomManger;

    private CustomLayoutManger customLayoutManger;

    private CustomLayoutMnagerAdapter customLayoutMnagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_manager);
        ButterKnife.bind(this);
        customLayoutMnagerAdapter = new CustomLayoutMnagerAdapter(this);
        customLayoutManger = new CustomLayoutManger();
        recycleCustomManger.setLayoutManager(customLayoutManger);
        recycleCustomManger.setAdapter(customLayoutMnagerAdapter);
    }
}
