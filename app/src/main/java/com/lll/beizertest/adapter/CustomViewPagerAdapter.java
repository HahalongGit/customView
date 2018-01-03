package com.lll.beizertest.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lll.beizertest.R;
import com.lll.beizertest.activity.CustomViewPagerActivity;

/**
 * Created by longlong on 2018/1/3.
 *
 * @ClassName: CustomViewPagerAdapter
 * @Description:
 * @Date 2018/1/3
 */

public class CustomViewPagerAdapter extends PagerAdapter {

    private Context context;

    int[] imgRes = {R.mipmap.bg_blue, R.mipmap.bg_orange, R.mipmap.bg_pink,R.mipmap.bg_puple};

    public CustomViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgRes.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(context);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setImageResource(imgRes[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}