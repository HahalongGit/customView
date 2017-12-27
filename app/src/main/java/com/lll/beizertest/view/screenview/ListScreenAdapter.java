package com.lll.beizertest.view.screenview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lll.beizertest.R;


import java.lang.ref.PhantomReference;

/**
 * Created by longlong on 2017/12/27.
 *
 * @ClassName: ListScreenAdapter
 * @Description:
 * @Date 2017/12/27
 */

public class ListScreenAdapter extends BaseMenuAdapter {

    private String mItes[] = {"类型","品牌","价格","更多"};

    private Context context;

    public ListScreenAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_screen_tab_layout,parent,false);
        TextView textView =  view.findViewById(R.id.tv_tabText);
        textView.setText(mItes[position]);
        return view;
    }

    @Override
    public int getCount() {
        return mItes.length;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_screen_menu_layout,parent,false);
        TextView menuView =  view.findViewById(R.id.tv_menuText);
        menuView.setText(mItes[position]);
        return view;
    }

    @Override
    public void menuOpen(View tabView) {
        super.menuOpen(tabView);
        TextView text = (TextView) tabView;
        text.setTextColor(Color.RED);
        //处理Tab 点击打开后变化
    }

    @Override
    public void menuClose(View tabView) {
        super.menuClose(tabView);
        //处理Tab 点击关闭后变化
        TextView text = (TextView) tabView;
        text.setTextColor(Color.BLACK);
    }
}
