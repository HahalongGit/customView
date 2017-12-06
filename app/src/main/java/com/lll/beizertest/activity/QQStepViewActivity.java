package com.lll.beizertest.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.lll.beizertest.R;
import com.lll.beizertest.view.QQStepView;

public class QQStepViewActivity extends AppCompatActivity implements View.OnClickListener {

    QQStepView qqStepView;

    private int maxStep = 4000;

    private int currentStep = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    currentStep++;
                    if (currentStep <= maxStep) {
                        qqStepView.setCurrentStep(currentStep);
                        handler.sendEmptyMessageDelayed(1, 100);
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqstep_view);
        findViewById(R.id.btn_start).setOnClickListener(this);
        qqStepView = findViewById(R.id.stepView);
        qqStepView.setStepMax(maxStep);
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 3000);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                qqStepView.setCurrentStep((int)value);
            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: {
                currentStep = 0;
                handler.sendEmptyMessage(1);
                break;
            }
        }
    }
}
