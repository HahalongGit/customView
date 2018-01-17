package com.lll.beizertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.R;
import com.lll.beizertest.view.recycleView.WrapAdapter;
import com.lll.beizertest.view.recycleView.WrapRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RecycleView 添加头部和尾部
 */
public class RecycleViewHeaderFooterActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    WrapRecyclerView recycleView;
    @BindView(R.id.btn_addHeaderView)
    Button btnAddHeaderView;
    @BindView(R.id.btn_addFooterView)
    Button btnAddFooterView;

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_header_footer);
        ButterKnife.bind(this);
        layoutManager =  new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        WrapAdapter adapter = new WrapAdapter(this) ;
        recycleView.setAdapter(adapter);
    }

    @OnClick({R.id.btn_addHeaderView, R.id.btn_addFooterView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_addHeaderView:
                View view1 = LayoutInflater.from(this).inflate(R.layout.header_view_layout,recycleView,false);
                recycleView.addHeadView(view1);
                break;
            case R.id.btn_addFooterView:
                View view2 = LayoutInflater.from(this).inflate(R.layout.footer_view_layout,recycleView,false);
                recycleView.addFootView(view2);
                break;
        }
    }
}
