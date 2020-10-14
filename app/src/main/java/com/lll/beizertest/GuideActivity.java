package com.lll.beizertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.test.MiddleStudent;
import com.lll.beizertest.test.Student;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 页面功能导航
 */
public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.btn_partOne)
    Button btnPartOne;
    @BindView(R.id.btn_partTwo)
    Button btnPartTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        Student student = new MiddleStudent();
        student.haveClass();//asdf
    }

    @OnClick({R.id.btn_partOne, R.id.btn_partTwo,R.id.btn_partThree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_partOne: {
                Intent intent = new Intent(this, PartOneActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_partTwo: {
                Intent intent = new Intent(this, PartTwoActivity.class);
                startActivity(intent);
                break;
            } case R.id.btn_partThree:{
                Intent intent = new Intent(this, PartThreeActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
