package com.lll.beizertest.view.recycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by longlong on 2018/1/17.
 *
 * @ClassName: WrapAdapter
 * @Description:  RecyclerView item适配器
 * @Date 2018/1/17
 */

public class WrapAdapter extends RecyclerView.Adapter {

    private Context context;

    public WrapAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WrapViewHolder viewHolder = new WrapViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_adapter_item_layout,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        WrapViewHolder wrapViewHolder = (WrapViewHolder) holder;
        wrapViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class WrapViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_imageshow)
        ImageView imageView;

        public WrapViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
