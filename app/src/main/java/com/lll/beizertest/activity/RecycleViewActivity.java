package com.lll.beizertest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.R;
import com.lll.beizertest.activity.recycleview.CustomLayoutManagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RecycleView 专题
 */
public class RecycleViewActivity extends AppCompatActivity {

    @BindView(R.id.btn_customLayoutManager)
    Button btnCustomLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_customLayoutManager)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_customLayoutManager: {
                Intent intent = new Intent(this, CustomLayoutManagerActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
