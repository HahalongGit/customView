package com.lll.beizertest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lll.beizertest.R;

import java.util.List;

/**
 * Created by longlong on 2017/12/15.
 *
 * @ClassName: ListAdapter
 * @Description: 测试适配器
 * @Date 2017/12/15
 */

public class ListAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;

    public ListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_adapter_item_layout,parent,false);
        TextView textView = view.findViewById(R.id.tv_listItem);
        textView.setText(list.get(position));
        return view;
    }
}
