package com.lll.beizertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义图片选择器 测试
 */
public class ImageSelectTestActivity extends AppCompatActivity {

    @BindView(R.id.recycle_selectResult)
    RecyclerView recycleSelectResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select_test);
        ButterKnife.bind(this);
    }
}
