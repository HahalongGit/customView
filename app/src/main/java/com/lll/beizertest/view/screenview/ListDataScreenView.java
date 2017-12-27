package com.lll.beizertest.view.screenview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by longlong on 2017/12/27.
 *
 * @ClassName: ListDataScreenView
 * @Description: 顶部点击菜单展开，筛选等功能的实现。
 * @Date 2017/12/27
 */

public class ListDataScreenView extends LinearLayout implements View.OnClickListener {

    /**
     * 头部Menu
     */
    private LinearLayout mMenuTabLayout;

    private Context mContext;

    /**
     * 存放菜单内容，加菜单内容布局
     */
    private FrameLayout mMenuMiddleView;

    /**
     * 存放内容
     */
    private FrameLayout mMenuContainerLayout;

    /**
     * 阴影
     */
    private View mShadowView;

    /**
     * 阴影颜色
     */
    private int SHADOW_BACKGROUND = 0x88999999;

    /**
     * 筛选菜单Adapter
     */
    private BaseMenuAdapter mBaseMenuAdapter;

    private int mContainerHeight;

    /**
     * 当前操作位置
     */
    private int mCurrentPosition = -1;

    /**
     * 动画是否执行
     */
    private boolean isAnimatorExecutor;

    /**
     * 设置 筛选View Adapter
     *
     * @param baseMenuAdapter
     */
    public void setAdapter(BaseMenuAdapter baseMenuAdapter) {
        if (baseMenuAdapter == null) {
            new RuntimeException("BaseMenuAdapter 不能为null");
        }
        this.mBaseMenuAdapter = baseMenuAdapter;
        int count = mBaseMenuAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View tabView = mBaseMenuAdapter.getTabView(i, mMenuTabLayout);
            LayoutParams layoutParams = (LayoutParams) tabView.getLayoutParams();
            setTabClick(tabView, i);
            layoutParams.weight = 1;
            mMenuTabLayout.addView(tabView);
            //设置TextView 宽度等宽
            View menuView = mBaseMenuAdapter.getMenuView(i, mMenuContainerLayout);
            menuView.setVisibility(GONE);
            mMenuContainerLayout.addView(menuView);
        }

    }

    private void setTabClick(final View tabView, final int position) {

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //动画执行的时候不相应点击事件
                if (mCurrentPosition == -1) {//-1 没有打开
                    openMenu(tabView, position);
                } else {//重新点击
                    if (mCurrentPosition != position) {
                        View curentMenu = mMenuContainerLayout.getChildAt(mCurrentPosition);
                        curentMenu.setVisibility(GONE);
                        mBaseMenuAdapter.menuClose(mMenuTabLayout.getChildAt(mCurrentPosition));//旧的设置还原
                        mCurrentPosition = position;//重新点击位置
                        View newClickMenu = mMenuContainerLayout.getChildAt(mCurrentPosition);
                        newClickMenu.setVisibility(VISIBLE);
                        mBaseMenuAdapter.menuOpen(mMenuTabLayout.getChildAt(mCurrentPosition));//新的，更新
                    } else {
                        closeMenu();
                    }

                }
            }


        });
    }

    private void closeMenu() {
        if (isAnimatorExecutor) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mMenuContainerLayout, "translationY", 0, -mContainerHeight);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f);
        alpha.setDuration(500);
        //等关闭动画执行完成，才去隐藏
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                View childAt = mMenuContainerLayout.getChildAt(mCurrentPosition);
                childAt.setVisibility(VISIBLE);
                mShadowView.setVisibility(GONE);//关闭，不占用Click
                mCurrentPosition = -1;
                isAnimatorExecutor = false;

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimatorExecutor = true;
                //Tab 关闭的时候
                mBaseMenuAdapter.menuClose(mMenuTabLayout.getChildAt(mCurrentPosition));
            }
        });
        alpha.start();
    }

    private void openMenu(final View tabView, final int position) {
        if (isAnimatorExecutor) {
            return;
        }
        mShadowView.setVisibility(VISIBLE);//开始的时候显示，动画设置透明度
        //点击打开的时候显示当前位置的内容
        View childAt = mMenuContainerLayout.getChildAt(position);
        childAt.setVisibility(VISIBLE);//显示子View
        Log.e("TAG", "position:" + position + ",childAt:" + childAt);//从后边往前边点击，获取到的子View是正确的，显示的是最后边获取的子View，
        //是不是覆盖了

        ObjectAnimator translation = ObjectAnimator.ofFloat(mMenuContainerLayout, "translationY", -mContainerHeight, 0);
        translation.setDuration(500);
        translation.start();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f);
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimatorExecutor = false;
                mCurrentPosition = position;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimatorExecutor = true;
                mBaseMenuAdapter.menuOpen(tabView);//Tab 打开的时候
            }
        });
        alpha.setDuration(500);
        alpha.start();

    }

    public ListDataScreenView(Context context) {
        this(context, null);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    public void initView() {
        //1.布局创建View ，加载布局想过
        //2.代码创建布局
        //-----------------------------//
        //1.创建头部，存放Tab
        setOrientation(VERTICAL);
        mMenuTabLayout = new LinearLayout(mContext);
        mMenuTabLayout.setOrientation(HORIZONTAL);
        mMenuTabLayout.setClickable(true);
        mMenuTabLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //mMenuTabLayout.setBackgroundColor(Color.RED);
        addView(mMenuTabLayout);
        //2.创建FrameLayout 存放筛选内容和阴影部分。
        mMenuMiddleView = new FrameLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;//占除掉Tab以为的部分
        mMenuMiddleView.setLayoutParams(params);
        addView(mMenuMiddleView);

        //阴影
        mShadowView = new View(mContext);
        mShadowView.setOnClickListener(this);
        mShadowView.setBackgroundColor(SHADOW_BACKGROUND);
        mShadowView.setVisibility(GONE);//开始隐藏阴影
        mMenuMiddleView.addView(mShadowView);
        //菜单内容
        mMenuContainerLayout = new FrameLayout(mContext);
        mMenuContainerLayout.setBackgroundColor(Color.WHITE);
        mMenuContainerLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mMenuMiddleView.addView(mMenuContainerLayout);

        //3.设置适配器添加数据
        //4.设置点击效果，添加动画等，设置点击切换页面View 数据，当前是打开的状态时不关闭，直接切换
        //5开始执行，结束执行的时候，传递当前Tab 给用户处理颜色和其他
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //onMesaure 会执行多次
        //内容的高度不是全部，是屏幕高度的75%左右，其他25%左右时候阴影
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mContainerHeight == 0 && height > 0) {
            mContainerHeight = (int) (height / 100f * 75);
            ViewGroup.LayoutParams layoutParams = mMenuContainerLayout.getLayoutParams();
            layoutParams.height = mContainerHeight;
            mMenuContainerLayout.setLayoutParams(layoutParams);
            mMenuContainerLayout.setTranslationY(mContainerHeight);
        }
    }

    @Override
    public void onClick(View v) {
        closeMenu();
    }
}
