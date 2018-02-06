package com.lll.beizertest.view.customviewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2018/2/6.
 *
 * @ClassName: MyBannerAdapter
 * @Description:
 * @Date 2018/2/6
 */

public class MyBannerAdapter extends PagerAdapter {

    private int[] imgRes = {R.mipmap.bg_blue, R.mipmap.bg_orange, R.mipmap.bg_pink, R.mipmap.bg_puple};

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
