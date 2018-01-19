package com.lll.beizertest.view.bannerViewPager;

import android.view.View;

/**
 * Created by longlong on 2018/1/19.
 *
 * @ClassName: BannerAdapter
 * @Description:
 * @Date 2018/1/19
 */

public abstract class BannerAdapter {

    /**
     * 根据位置获取ViewPager的View
     * @param position
     * @return
     */
    public abstract View getView(int position);

    /**
     * 获取数量
     * @return
     */
    public abstract int getCount();

    /**
     * 根据位置获取广告位描述 （有就覆盖，没有就不覆盖）
     * @param mCurrentPosition
     * @return
     */
    public String getBannerDescribe(int mCurrentPosition){
        return "";
    }
}
