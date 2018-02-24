package com.lll.beizertest.view.customviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2018/2/6.
 *
 * @ClassName: MyBannerAdapter
 * @Description:
 * @Date 2018/2/6
 */

public class MyBannerAdapter extends BannerViewAdapter {

    private Context context;

    public MyBannerAdapter(Context context) {
        this.context = context;
    }

    private int[] imgRes = {R.mipmap.bg_blue, R.mipmap.bg_orange, R.mipmap.bg_pink, R.mipmap.bg_puple};

    private OnItemTouchListener onItemTouchListener;

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    @Override
    public View getView(final int position, ViewGroup convertView) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.list_transformer_adapter_layout, convertView, false);
        ImageView imageView = view1.findViewById(R.id.imageView);
        int realPosition = position % 4;
        imageView.setImageResource(imgRes[realPosition]);
        ItemViewHolder itemViewHolder = new ItemViewHolder();
        itemViewHolder.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public boolean onItemClickListener(View v, MotionEvent event) {
                Toast.makeText(context, "点击l"+position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return view1;
    }

    @Override
    public int getCount() {
        return imgRes.length;
    }


    class ItemViewHolder implements View.OnTouchListener{

        private OnItemTouchListener onItemTouchListener;

        public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
            this.onItemTouchListener = onItemTouchListener;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(onItemTouchListener!=null){
                return onItemTouchListener.onItemClickListener(v,event);
            }
            return false;
        }
    }

    interface OnItemTouchListener{
        boolean onItemClickListener(View v, MotionEvent event);

    }

}
