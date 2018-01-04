package com.lll.beizertest.transformer;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by longlong on 2018/1/3.
 *
 * @ClassName: ScaleTransformer
 * @Description: 滑动缩放的Transformer
 * @Date 2018/1/3
 */

public class ScaleTransformer implements ViewPager.PageTransformer {

    /**
     * 最小的缩放是0.85
     */
    private static final float MIN_SCALE = 0.85f;

    private float elevation;

    private Context context;

    public ScaleTransformer(Context context) {
        this.context = context;
        elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, context.getResources().getDisplayMetrics());
    }

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) { // [-1,1]
            if (position < 0) {
                Log.e("TAG","transformPage-position < 0="+position);
                float scaleX = 1 + 0.15f * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
                ((CardView) page).setCardElevation((1 + position) * elevation);
            } else {
                Log.e("TAG","transformPage-position > 0="+position);
                float scaleX = 1 - 0.15f * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
                ((CardView) page).setCardElevation((1 - position) * elevation);
            }
            //page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }
}
