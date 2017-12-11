package com.lll.beizertest.view;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longlong on 2017/12/11.
 *
 * @ClassName: BaseAdapter
 * @Description: 自定义TagLayout 设置适配器
 * @Date 2017/12/11
 */

public abstract class BaseAdapter {

    /**
     * 获取数量
     * @return
     */
    public abstract int getCount();

    /**
     * 设置View
     * @param position
     * @param parent
     * @return
     */
    public abstract View getView(int position ,ViewGroup parent);

}
