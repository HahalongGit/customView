package com.lll.beizertest.draw.animator;

import android.view.animation.Interpolator;

/**
 * 参考LinearInterpolator 知道      public float getInterpolation 返回值
 *
 * @author RunningDigua
 * @date 2020/4/22
 */
public class MyInterplator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return 1-input;
    }
}
