package com.lll.beizertest.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lll.beizertest.R;
import com.lll.beizertest.view.ProgressBar;

public class DefineProgressBarActivity extends AppCompatActivity {

    private ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_progress_bar);
        myProgressBar = findViewById(R.id.myProgressBar);
        myProgressBar.setMaxProgress(1000);
        findViewById(R.id.btn_startProgress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator animator = ObjectAnimator.ofFloat(0, 1000);
                animator.setDuration(5000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        myProgressBar.setCurrentProgress((int) value);
                    }
                });
                animator.start();
            }
        });
    }
}
