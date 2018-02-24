package com.lll.beizertest.view.toolbar;

/**
 * Created by longlong on 2018/2/24.
 *
 * @ClassName: INavigationBar
 * @Description: 导航栏规范
 * @Date 2018/2/24
 */

public interface INavigationBar {

    /**
     * 绑定布局
     * @return
     */
    public int bindLayoutId();

    /**
     * 绑定View 数据
     */
    public void applyView();

}
