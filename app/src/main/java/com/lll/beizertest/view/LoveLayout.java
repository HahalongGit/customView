package com.lll.beizertest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lll.beizertest.R;

import java.util.Random;

/**
 * Created by longlong on 2017/11/21.
 *
 * @ClassName: LoveLayout
 * @Description: 模拟 直播点赞效果
 * @Date 2017/11/21
 */

public class LoveLayout extends RelativeLayout {

    /**
     * 随机数
     */
    private Random random;

    /**
     * 图片集合
     */
    private int[] imageLenght = new int[]{};
    private PointF p3;

    private int mWidth;

    private int mHeight;

    /**
     * 图片的宽
     */
    private int mDrawableWidth;

    /**
     * 图片的高
     */
    private int mDrawableHeight;

    /**
     * 插值器
     */
    private Interpolator mInterpolator[];

    public LoveLayout(Context context) {
        this(context, null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        random = new Random();
        Drawable drawable = ContextCompat.getDrawable(getContext(),R.mipmap.ic_launcher);
        mDrawableWidth = drawable.getIntrinsicWidth();//获取图片的宽
        mDrawableHeight = drawable.getIntrinsicHeight();//获取图片的高
        //插值器集合
        mInterpolator = new Interpolator[]{new AccelerateDecelerateInterpolator(),
        new AccelerateInterpolator(),
        new DecelerateInterpolator(),
        new LinearInterpolator()};
    }

    public void addLove() {
        final ImageView imageView = new ImageView(getContext());
        //random.nextInt(imageLenght.length-1);//取随机数
        imageView.setImageResource(R.mipmap.ic_launcher);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(CENTER_HORIZONTAL);
        imageView.setLayoutParams(layoutParams);
        addView(imageView);
        AnimatorSet objectAnimator = getAnimatorSet(imageView);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
               removeView(imageView);
            }
        });
        objectAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    private AnimatorSet getAnimatorSet(ImageView iv) {
        AnimatorSet allAnimatorSet = new AnimatorSet();

        //内部动画
        AnimatorSet innerAnimator = new AnimatorSet();
        //位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0.3f, 1.0f);
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0.3f, 1.0f);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 0.3f, 1.0f);
        //innerAnimator.setDuration(200);
        //一起执行
        innerAnimator.playTogether(objectAnimator, xAnimator, yAnimator);
        allAnimatorSet.setDuration(2000);
        allAnimatorSet.playSequentially(innerAnimator, getBeizerAnimator(iv));
        return allAnimatorSet;
    }

    /**
     * https://baike.baidu.com/item/%E8%B4%9D%E5%A1%9E%E5%B0%94%E6%9B%B2%E7%BA%BF/1091769?fr=aladdin
     * 贝塞尔三阶曲线图，两个控制点，一个起点，一个终点，画出S型
     * 四阶贝塞尔，三个控制点，一个起点一个终点
     *
     * @return
     */
    private Animator getBeizerAnimator(final ImageView iv) {
        PointF p0 = new PointF(mWidth/2-mDrawableWidth/2,mHeight-mDrawableHeight/2);//底部的中心点(0,0)在左上角,向下为Y正向，向右为X正向
        PointF p1 = getPoint(1);//x 在水平的任意位置，y 在比p0小的范围内，比p2大的范围内
        PointF p2 = getPoint(2);;//x 在水平的任意位置，y 在比p0小的范围内，比p2大的范围内
        PointF p3 = new PointF(random.nextInt(mWidth)-mDrawableWidth,0);//
        LoveTypeEvalautor loveTypeEvalautor = new LoveTypeEvalautor(p1, p2);//两个控制点

        //第一个参数是loveTypeEvalautor，第二个参数是p0,第三个参数是p3
        ValueAnimator bizerAnimator = ObjectAnimator.ofObject(loveTypeEvalautor, p0, p3);//c起点和终点
        bizerAnimator.setInterpolator(mInterpolator[random.nextInt(mInterpolator.length-1)]);//添加插值器
        bizerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
               iv.setX(pointF.x);
               iv.setY(pointF.y);
               float t = valueAnimator.getAnimatedFraction();
               iv.setAlpha(1-t+0.2f);
            }
        });
        return bizerAnimator;
    }

    private PointF getPoint(int i) {
       return new PointF(random.nextInt(mWidth)-mDrawableWidth,random.nextInt(mHeight/2)+mHeight*(i-1)/2);
    }

}
