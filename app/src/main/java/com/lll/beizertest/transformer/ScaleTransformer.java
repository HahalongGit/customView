package com.lll.beizertest.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by longlong on 2018/1/3.
 *
 * @ClassName: ScaleTransformer
 * @Description:
 * @Date 2018/1/3
 */

public class ScaleTransformer implements ViewPager.PageTransformer {

    public static final float MAX_SCALE = 1.2f;
    public static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {

        final float scaleFactor = MIN_SCALE+(1-MIN_SCALE)*(1-Math.abs(position));

//        if (position < -1) {
//            position = -1;
//        } else if (position > 1) {
//            position = 1;
//        }

        if (position < 0) { // [-1,0]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else if (position == 0) {
            page.setScaleX(1);
            page.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }

    }
}
