package com.lll.beizertest.view.bannerViewPager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lll.beizertest.R;

/**
 * Created by longlong on 2018/1/19.
 *
 * @ClassName: BannerView
 * @Description: 自定义BannerView
 * @Date 2018/1/19
 */

public class BannerView extends RelativeLayout {

    /**
     * 轮播的ViewPager
     */
    private BannerViewPager mBannerVp;

    /**
     * 描述
     */
    private TextView mbannerDescribe;

    /**
     * 指示器容器
     */
    private LinearLayout mDoinContainer;

    private BannerAdapter mBannerAdaper;

    private Context mContext;

    /**
     * 初始化点的选中的 背景Drawawble
     */
    private Drawable mDotIndicatorFoucsDrawable;
    /**
     * 默认的Drawable
     */
    private Drawable mDotIndicatorNormalDrawable;

    /**
     * 当前选中
     */
    private int mCurrentPosition = 0;

    /**
     * 点的位置
     */
    private int dotGravity = 0;

    /**
     * 底部背景色
     */
    private RelativeLayout mBannerBottomLayout;

    private int dotDistance;

    private int bottomColor;

    private int dotSize;

    /**
     * 宽度的比例
     */
    private float mWidthProportion;

    /**
     * 高度的比例
     */
    private float mHeightProportity;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //把布局加载到View中来
        inflate(context, R.layout.ui_banner_view_layout, this);
        mContext = context;
        initView();
        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mDotIndicatorNormalDrawable = a.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        mDotIndicatorFoucsDrawable = a.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mDotIndicatorFoucsDrawable == null) {
            mDotIndicatorFoucsDrawable = new ColorDrawable(Color.RED);
        }
        if (mDotIndicatorNormalDrawable == null) {
            mDotIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        bottomColor = a.getColor(R.styleable.BannerView_bottomColor, Color.TRANSPARENT);
        dotDistance = (int) a.getDimension(R.styleable.BannerView_dotDistance, dp2px(10));
        dotSize = (int) a.getDimension(R.styleable.BannerView_dotSize, dp2px(10));
        dotGravity = a.getInt(R.styleable.BannerView_dotGravity, dotGravity);
        mWidthProportion = a.getFloat(R.styleable.BannerView_widthProportion, mWidthProportion);
        mHeightProportity = a.getFloat(R.styleable.BannerView_hightProportion, mHeightProportity);
        a.recycle();
    }

    private void initView() {
        mBannerVp = findViewById(R.id.bannerVp);
        mbannerDescribe = findViewById(R.id.tv_bannerDescribe);
        mDoinContainer = findViewById(R.id.doinContainer);
        mBannerBottomLayout = findViewById(R.id.bannerBottomLayout);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (mHeightProportity == 0 || mHeightProportity == 0) {
//            return;
//        }
//        //动态设置宽高比
//        int wodth = MeasureSpec.getSize(widthMeasureSpec);
//        int height = (int) ((mHeightProportity / mWidthProportion) * wodth);
//        setMeasuredDimension(widthMeasureSpec, height);
//    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setBannerAdapter(BannerAdapter adapter) {
        this.mBannerAdaper = adapter;
        mBannerVp.setAdapter(adapter);
        //初始化点 指示器
        initDotIndictor();
        //bug修复
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //监听当前选中的位置
                pageSelect(position);
            }
        });
        mbannerDescribe.setText(mBannerAdaper.getBannerDescribe(0));//设置第一条描述

        post(new Runnable() {
            @Override
            public void run() {
                if (mHeightProportity == 0 || mHeightProportity == 0) {
                    return;
                }
                //加载数据之后，根据获取的数据的图片比例，动态设置宽高比，
                // onMeasure中还没有获取到数据，viewPager高度是0,加载数据之后设置高度
                int width = getMeasuredWidth();
                Log.e("TAG","mHeightProportity-MeasuredWidth:"+width);
                int height = (int) (mHeightProportity / mWidthProportion * width);
                Log.e("TAG","mHeightProportity-height:"+height);
                getLayoutParams().height = height;
            }
        });

    }

    /**
     * 页面切换回调
     *
     * @param position
     */
    private void pageSelect(int position) {
        //把之前的位置的点设置为默认、
        DotInticatorView oldDotIndicatorView = (DotInticatorView) mDoinContainer.getChildAt(mCurrentPosition);
        oldDotIndicatorView.setDrawable(mDotIndicatorNormalDrawable);
        //把当前位置的点设置为选中 position - 2的31（Integer的最大值）
        mCurrentPosition = position % mBannerAdaper.getCount();
        DotInticatorView newDotIndicatorView = (DotInticatorView) mDoinContainer.getChildAt(mCurrentPosition);
        newDotIndicatorView.setDrawable(mDotIndicatorFoucsDrawable);

        //设置广告描述
        String bannerDes = mBannerAdaper.getBannerDescribe(mCurrentPosition);
        mbannerDescribe.setText(bannerDes);
    }

    /**
     * 初始化指示器
     */
    private void initDotIndictor() {
        int count = mBannerAdaper.getCount();
        for (int i = 0; i < count; i++) {
            //添加指示器
            DotInticatorView dotInticatorView = new DotInticatorView(mContext);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.leftMargin = dotDistance;
            dotInticatorView.setLayoutParams(layoutParams);
            if (i == 0) {
                dotInticatorView.setDrawable(mDotIndicatorFoucsDrawable);
            } else {
                dotInticatorView.setDrawable(mDotIndicatorNormalDrawable);
            }
            mDoinContainer.addView(dotInticatorView);
            mDoinContainer.setGravity(getDotGravity(dotGravity));//设置点的位置在Right
            mBannerBottomLayout.setBackgroundColor(bottomColor);//底部背景色
        }
    }

    private int getDotGravity(int dotGravity) {
        switch (dotGravity) {
            case 0: {
                return Gravity.CENTER;
            }
            case 1: {
                return Gravity.RIGHT;
            }
            case -1: {
                return Gravity.LEFT;
            }
        }
        return Gravity.RIGHT;
    }

    /**
     * @param reverseDrawingOrder
     * @param transformer
     */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mBannerVp.setPageTransformer(reverseDrawingOrder, transformer);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
    }

    /**
     * 开始滚动
     */
    public void starRoll() {
        mBannerVp.startRoll();
    }


}
