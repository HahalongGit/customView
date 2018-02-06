package com.lll.beizertest.view.customviewpager;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longlong on 2018/2/6.
 *
 * @ClassName: BannerViewAdapter
 * @Description: 自定义Adapter
 * @Date 2018/2/6
 */

public  abstract class BannerViewAdapter {

    public abstract  View getView(int position,ViewGroup convertView);

    public abstract int getCount();


}
