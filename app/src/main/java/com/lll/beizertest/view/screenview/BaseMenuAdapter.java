package com.lll.beizertest.view.screenview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longlong on 2017/12/27.
 *
 * @ClassName: BaseMenuAdapter
 * @Description: 筛选View BaseMenuAdapter
 * @Date 2017/12/27
 */

public abstract class BaseMenuAdapter {

    /**
     *
     * @return
     */
    public abstract View getTabView(int position, ViewGroup parent);

    /**
     * 获取条数
     * @return
     */
    public abstract int getCount();

    /**
     * 获取菜单内容
     * @param position
     * @param parent
     * @return
     */
    public abstract View getMenuView(int position, ViewGroup parent);


    /**
     * 菜单打开
     * @param tabView
     */
    public void menuOpen(View tabView) {

    }

    /**
     * 菜单关闭
     * @param tabView
     */
    public void menuClose(View tabView) {

    }
}
