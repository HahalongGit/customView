package com.lll.beizertest.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lll.beizertest.R;

public class AnimatorActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        imageView = findViewById(R.id.iv_animator);
        findViewById(R.id.btn_start).setOnClickListener(this);
        imageView.setOnClickListener(this);

        //animator1();
//        BitmapFactory.Options options = new  BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        options.inSampleSize = 2;
//        options.inJustDecodeBounds = false;

    }

    /**
     * test 1
     */
    private void animator1() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,5f,0f);


        ObjectAnimator o = ObjectAnimator.ofFloat(valueAnimator,"translationX",3,4);
        o.start();
        valueAnimator.setDuration(1000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.e("TAG","value:"+value);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:{
                //加载xml
                Animator animator = AnimatorInflater.loadAnimator(this,R.animator.animator1);
                animator.setTarget(imageView);
                animator.start();
                //代码执行动画
//                AnimatorSet animatorSet = new AnimatorSet();
//                ObjectAnimator translation = ObjectAnimator.ofFloat(imageView,"translationX",0f,300f,0f);
//                ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView,"alpha",1f,0f,1f);
//                ObjectAnimator rotationY = ObjectAnimator.ofFloat(imageView,"rotationY",0f,360f);
//                //animatorSet.playSequentially(translation,alpha,rotationY);
//                //animatorSet.play(translation).with(alpha).before(rotationY);
//                animatorSet.playTogether(translation,alpha,rotationY);
//                animatorSet.setDuration(3000);
//                animatorSet.start();
                break;
            }
            case R.id.iv_animator:{
                Toast.makeText(this, "点击图片", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
