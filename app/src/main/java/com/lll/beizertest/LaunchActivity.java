package com.lll.beizertest;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.lll.beizertest.activity.AnimatorActivity;
import com.lll.beizertest.activity.BehaviorActivity;
import com.lll.beizertest.activity.ColorTrackTextActivity;
import com.lll.beizertest.activity.DefineProgressBarActivity;
import com.lll.beizertest.activity.DrawLayoutActivity;
import com.lll.beizertest.activity.DrawShapeActivity;
import com.lll.beizertest.activity.LetterSideBarActivity;
import com.lll.beizertest.activity.LivePerformActivity;
import com.lll.beizertest.activity.LogisticsProgressViewActivity;
import com.lll.beizertest.activity.LuckPatternViewActivity;
import com.lll.beizertest.activity.MainActivity;
import com.lll.beizertest.activity.MyTextViewActivity;
import com.lll.beizertest.activity.OriginCodeAnalysisActivity;
import com.lll.beizertest.activity.QQStepViewActivity;
import com.lll.beizertest.activity.RatingBarActivity;
import com.lll.beizertest.activity.RoundImageActivity;
import com.lll.beizertest.activity.SignaturePadActivity;
import com.lll.beizertest.activity.StatusBarActivity;
import com.lll.beizertest.activity.TagLayoutActivity;
import com.lll.beizertest.activity.TouchViewActivity;
import com.lll.beizertest.activity.VerticalDragViewActivity;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener{

    //private ListView listView;;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
//            //5.0以上设置全屏
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        findViewById(R.id.btn_signature).setOnClickListener(this);
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
        findViewById(R.id.btn_luckPatternView).setOnClickListener(this);
        findViewById(R.id.btn_startBar).setOnClickListener(this);
        findViewById(R.id.btn_behavior).setOnClickListener(this);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,0.6f,0.2f,1f);
        valueAnimator.setDuration(500);
        valueAnimator.start();

        //listView.setAdapter();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signature:{
                Intent intent = new Intent(this,SignaturePadActivity.class);
                startActivity(intent);
                break;
            }
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
            case R.id.btn_luckPatternView:{
                Intent intent = new Intent(this,LuckPatternViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_startBar:{
                Intent intent = new Intent(this,StatusBarActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_behavior:{
                Intent intent = new Intent(this,BehaviorActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
