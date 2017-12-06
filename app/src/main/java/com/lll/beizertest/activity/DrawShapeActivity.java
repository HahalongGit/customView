package com.lll.beizertest.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lll.beizertest.R;
import com.lll.beizertest.view.ShapeView;

public class DrawShapeActivity extends AppCompatActivity {

    private ShapeView shapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_shape);
        shapeView = findViewById(R.id.shapeView);
        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","setOnClickListener执行");
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       while (true){
                           try {
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       shapeView.exChange();
                                   }
                               });
                               Thread.sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }

                   }
               }).start();
            }
        });
    }
}
