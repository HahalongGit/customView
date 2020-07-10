package com.lll.beizertest.draw;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * View
 * 绘制
 */
public class DrawViewActivity extends AppCompatActivity {

    @BindView(R.id.btn_test)
    Button mBtnTest;
    @BindView(R.id.btn_startAnimator)
    Button mBtnStartAnimator;

    private ValueAnimator mValueAnimator = ValueAnimator.ofInt(0, 600);

    /**
     * 操作属性的动画
     */
    private ObjectAnimator mObjectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view);
        ButterKnife.bind(this);
//        mObjectAnimator = ObjectAnimator.ofFloat(mBtnTest, "alpha", 1, 0, 1);// 变化范围0-1
//        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                mBtnTest.layout(mBtnTest.getLeft(),
//                        value, mBtnTest.getWidth(),
//                        mBtnTest.getHeight() + value);
//            }
//        });
//        mObjectAnimator.setDuration(1000);// ObjectAnimator
//        mValueAnimator.setDuration(1000);
////        mValueAnimator.setInterpolator(new LinearInterpolator());
//        mValueAnimator.setEvaluator(new MyIntEvaluator());
////        ArgbEvaluator argbEvaluator;
////        mBtnTest.setAlpha();
//        LayoutAnimationController layoutAnimationController;
//        GridLayoutAnimationController gridLayoutAnimationController;
    }

    @OnClick({R.id.btn_startAnimator,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_startAnimator: {
                if (mValueAnimator != null) {// 操作Value
                    mValueAnimator.start();
                }
                if (mObjectAnimator != null) {// 操作属性
                    mObjectAnimator.start();
                }
                break;
            }
        }
    }
}
