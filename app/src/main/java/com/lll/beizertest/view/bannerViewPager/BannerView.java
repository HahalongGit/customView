package com.lll.beizertest.view.bannerViewPager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //把布局加载到View中来
        inflate(context, R.layout.ui_banner_view_layout,this);
        mContext = context;
        initView();
        mDotIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        mDotIndicatorFoucsDrawable = new ColorDrawable(Color.RED);
    }

    private void initView() {
        mBannerVp = findViewById(R.id.bannerVp);
        mbannerDescribe = findViewById(R.id.tv_bannerDescribe);
        mDoinContainer = findViewById(R.id.doinContainer);

    }

    /**
     * 设置适配器
     * @param adapter
     */
    public void setBannerAdapter(BannerAdapter adapter){
        this.mBannerAdaper = adapter;
        mBannerVp.setAdapter(adapter);
        //初始化点 指示器
        initDotIndictor();
        //bug修复
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //监听当前选中的位置
                pageSelect(position);
            }
        });
        mbannerDescribe.setText(mBannerAdaper.getBannerDescribe(0));//设置第一条描述
    }

    /**
     * 页面切换回调
     * @param position
     */
    private void pageSelect(int position) {
        //把之前的位置的点设置为默认、
        DotInticatorView oldDotIndicatorView = (DotInticatorView) mDoinContainer.getChildAt(mCurrentPosition);
        oldDotIndicatorView.setDrawable(mDotIndicatorNormalDrawable);
        //把当前位置的点设置为选中 position - 2的31（Integer的最大值）
        mCurrentPosition = position%mBannerAdaper.getCount();
        DotInticatorView newDotIndicatorView = (DotInticatorView) mDoinContainer.getChildAt(mCurrentPosition);
        newDotIndicatorView.setDrawable(mDotIndicatorFoucsDrawable);

        //设置广告描述
        String bannerDes= mBannerAdaper.getBannerDescribe(mCurrentPosition);
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

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dp2px(8),dp2px(8));
            layoutParams.leftMargin = layoutParams.rightMargin = dp2px(2);
            dotInticatorView.setLayoutParams(layoutParams);
            if(i==0){
                dotInticatorView.setDrawable(mDotIndicatorFoucsDrawable);
            }else {
                dotInticatorView.setDrawable(mDotIndicatorNormalDrawable);
            }
            mDoinContainer.addView(dotInticatorView);
            mDoinContainer.setGravity(Gravity.RIGHT);//设置点的位置在Right
        }
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,mContext.getResources().getDisplayMetrics());
    }

    /**
     * 开始滚动
     */
    public void starRoll(){
        mBannerVp.startRoll();
    }

}
