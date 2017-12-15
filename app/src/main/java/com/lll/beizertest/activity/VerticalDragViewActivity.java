package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 竖值拖动View
 */
public class VerticalDragViewActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_drag_view);
        findViewById(R.id.tv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VerticalDragViewActivity.this, "点击menu", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VerticalDragViewActivity.this, "点击按钮 menu", Toast.LENGTH_SHORT).show();
            }
        });

        listView = findViewById(R.id.listView);

        list = new ArrayList<>();
        for(int i=0;i<=15;i++){
            list.add("item -"+i);
        }
        listView.setAdapter(new ListAdapter(this,list));
    }
}
