package com.lll.beizertest;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lll.beizertest.activity.AnimatorActivity;
import com.lll.beizertest.activity.ColorTrackTextActivity;
import com.lll.beizertest.activity.DefineProgressBarActivity;
import com.lll.beizertest.activity.DrawLayoutActivity;
import com.lll.beizertest.activity.DrawShapeActivity;
import com.lll.beizertest.activity.LetterSideBarActivity;
import com.lll.beizertest.activity.LivePerformActivity;
import com.lll.beizertest.activity.LogisticsProgressViewActivity;
import com.lll.beizertest.activity.MainActivity;
import com.lll.beizertest.activity.MyTextViewActivity;
import com.lll.beizertest.activity.OriginCodeAnalysisActivity;
import com.lll.beizertest.activity.QQStepViewActivity;
import com.lll.beizertest.activity.RatingBarActivity;
import com.lll.beizertest.activity.RoundImageActivity;
import com.lll.beizertest.activity.TagLayoutActivity;
import com.lll.beizertest.activity.TouchViewActivity;
import com.lll.beizertest.activity.VerticalDragViewActivity;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener{

    //private ListView listView;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        findViewById(R.id.btn_livePerform).setOnClickListener(this);
        findViewById(R.id.btn_animator).setOnClickListener(this);
        findViewById(R.id.btn_qqBeizer).setOnClickListener(this);
        findViewById(R.id.btn_defineText).setOnClickListener(this);
        findViewById(R.id.btn_qqStepView).setOnClickListener(this);
        findViewById(R.id.btn_trackText).setOnClickListener(this);
        findViewById(R.id.btn_progressBar).setOnClickListener(this);
        findViewById(R.id.btn_shapeView).setOnClickListener(this);
        findViewById(R.id.btn_ratingBar).setOnClickListener(this);
        findViewById(R.id.btn_letterSideBar).setOnClickListener(this);
        findViewById(R.id.btn_originCode).setOnClickListener(this);
        findViewById(R.id.btn_customViewGroup).setOnClickListener(this);
        findViewById(R.id.btn_touchView).setOnClickListener(this);
        findViewById(R.id.btn_drawLayout).setOnClickListener(this);
        findViewById(R.id.btn_roundImage).setOnClickListener(this);
        findViewById(R.id.btn_logisticsProgress).setOnClickListener(this);
        findViewById(R.id.btn_verticalDragView).setOnClickListener(this);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,0.6f,0.2f,1f);
        valueAnimator.setDuration(500);
        valueAnimator.start();

        //listView.setAdapter();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_qqBeizer:{
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_animator:{
                Intent intent = new Intent(this,AnimatorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_livePerform:{
                Intent intent = new Intent(this,LivePerformActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_defineText:{
                Intent intent = new Intent(this,MyTextViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_qqStepView:{
                Intent intent = new Intent(this,QQStepViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_trackText:{
                Intent intent = new Intent(this,ColorTrackTextActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_progressBar:{
                Intent intent = new Intent(this,DefineProgressBarActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_shapeView:{
                Intent intent = new Intent(this,DrawShapeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_ratingBar:{
                Intent intent = new Intent(this,RatingBarActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_letterSideBar:{
                Intent intent = new Intent(this,LetterSideBarActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_originCode:{
                Intent intent = new Intent(this,OriginCodeAnalysisActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_customViewGroup:{
                Intent intent = new Intent(this,TagLayoutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_touchView:{
                Intent intent = new Intent(this,TouchViewActivity.class);
                startActivity(intent);
                break;
            }case R.id.btn_drawLayout:{
                Intent intent = new Intent(this,DrawLayoutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_roundImage:{
                Intent intent = new Intent(this,RoundImageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_logisticsProgress:{
                Intent intent = new Intent(this,LogisticsProgressViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_verticalDragView:{
                Intent intent = new Intent(this,VerticalDragViewActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
