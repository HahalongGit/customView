package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.lll.beizertest.R;
import com.lll.beizertest.view.screenview.BaseMenuAdapter;
import com.lll.beizertest.view.screenview.ListDataScreenView;
import com.lll.beizertest.view.screenview.ListScreenAdapter;

/**
 * 筛选View ScreenMenuView
 * 筛选 Screen
 */
public class ScreenMenuViewActivity extends AppCompatActivity {

    private ListDataScreenView listDataScreenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_menu_view);
        listDataScreenView = findViewById(R.id.listDataScreenView);
        ListScreenAdapter adapter = new ListScreenAdapter(this);
        listDataScreenView.setAdapter(adapter);
    }

}
