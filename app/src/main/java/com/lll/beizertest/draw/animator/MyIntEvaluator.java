package com.lll.beizertest.draw.animator;

import android.animation.TypeEvaluator;

/**
 * 自定Evaluator
 *
 * @author RunningDigua
 * @date 2020/4/22
 */
public class MyIntEvaluator implements TypeEvaluator<Integer> {

//    @Override
//    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
//        float value = 200 + startValue + fraction * (endValue - startValue);
//        int result = (int) value;
//        return result;
//    }

    //TODO ValueAnimator.offInt offObject方法，offObject方法可以处理任何对象的参数，需要我们自定义Evalutor
    //TODO 注意：这里使用的是ValueAnimator的offObject方法而不是ObjectAnimator类的方法

    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        //fraction * (endValue - startValue))表示已经运动的距离，
        // endValue- fraction * (endValue - startValue))就距离终点yu
        // 返回的类型和ValueAnimator 调用的方法offInt是同一个类型才能使用，否则报错转换异常。
        return (int) (endValue - fraction * (endValue - startValue));
    }

}
