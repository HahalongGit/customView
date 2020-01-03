package com.lll.beizertest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 滑动的View
 */
public class ScrollViewActivity extends AppCompatActivity {


    private static final String TAG = "ScrollViewActivity";

    @BindView(R.id.btn_operation)
    Button mBtnOperation;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.ll_bottomOperationView)
    LinearLayout mLlBottomOperationView;

    private ValueAnimator mValueAnimator;

    private boolean isClose;

    private int mAddressHeight;

    private int mWashcarHeight = 109;

    @BindView(R.id.tv_washCar)
    TextView mTvWashCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        ButterKnife.bind(this);
        mValueAnimator = ValueAnimator.ofFloat(1, 0);
        mValueAnimator.setDuration(150);

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTvAddress.getLayoutParams();
                layoutParams.height = (int) (animatedValue * mAddressHeight);
                mTvAddress.setLayoutParams(layoutParams);

                Log.e(TAG, "ValueAnimator-rate-:" + (1 - animatedValue));
                Log.e(TAG, "ValueAnimator--height:" + ((1 - animatedValue) * mWashcarHeight));
                ViewGroup.LayoutParams params = mTvWashCar.getLayoutParams();
                params.height = (int) ((1 - animatedValue) * mWashcarHeight);
                mTvWashCar.setLayoutParams(params);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int measuredHeight = mTvAddress.getMeasuredHeight();
        int height = mTvWashCar.getHeight();
        mAddressHeight = measuredHeight;
        Log.e(TAG, "onWindowFocusChanged-measuredHeight:" + measuredHeight);
        Log.e(TAG, "onWindowFocusChanged-height:" + height);
    }

    @OnClick({R.id.btn_operation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_operation: {
                Log.e(TAG, "onViewClicked--");
                if (isClose && !mValueAnimator.isRunning()) {
                    mValueAnimator.setFloatValues(0, 1);
                    isClose = false;
                } else {
                    isClose = true;
                    mValueAnimator.setFloatValues(1, 0);
                }

                if (mValueAnimator != null && !mValueAnimator.isRunning()) {
                    mValueAnimator.start();
                }
                break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mValueAnimator.cancel();
        mValueAnimator = null;
    }
}
