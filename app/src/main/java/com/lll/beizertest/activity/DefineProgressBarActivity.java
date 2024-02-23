package com.lll.beizertest.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lll.beizertest.R;
import com.lll.beizertest.view.ProgressBar;
import com.lll.beizertest.view.ZHProgressBar;

public class DefineProgressBarActivity extends AppCompatActivity {

    private ProgressBar myProgressBar;

    private ZHProgressBar mZHProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_progress_bar);
        myProgressBar = findViewById(R.id.myProgressBar);
        mZHProgressBar = findViewById(R.id.downloadProgress);
        myProgressBar.setMaxProgress(1000);
        mZHProgressBar.setMaxProgress(1000);
        findViewById(R.id.btn_startProgress).setOnClickListener(v -> {
            ValueAnimator animator = ObjectAnimator.ofFloat(0, 1000);
            animator.setDuration(5000);
            animator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                myProgressBar.setCurrentProgress((int) value);
                mZHProgressBar.setCurrentProgress((int)value);
            });
            animator.start();
        });
    }
}
