package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lll.beizertest.R;
import com.lll.beizertest.view.BaseAdapter;
import com.lll.beizertest.view.TagLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局定义
 */
public class TagLayoutActivity extends AppCompatActivity {

    private TagLayout tagLayout;

    private List<String> contents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_layout);
        tagLayout = findViewById(R.id.tagLayout);
        contents.add("tagLayout");
        contents.add("北京");
        contents.add("中国梦");
        contents.add("年轻有力量");
        contents.add("我de梦");
        contents.add("强军梦");
        contents.add("2018 中国年");
        contents.add("婚姻，爱情，家庭，教育");
        contents.add("2008 北京欢迎您，2018北京驱赶您！");
        contents.add("快速致富");
        //适配器模式设置添加 TagLayout 的View
        tagLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return contents.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = LayoutInflater.from(TagLayoutActivity.this).inflate(R.layout.recycle_adapter_item_tag_layout,parent,false);
                TextView textView = view.findViewById(R.id.tv_tagLayoutCcontent);
                textView.setText(contents.get(position));
                return view;
            }
        });
    }
}
