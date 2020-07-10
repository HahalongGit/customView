package com.lll.beizertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.activity.NestedScrollingActivity;
import com.lll.beizertest.activity.ScrollViewActivity;
import com.lll.beizertest.activity.provider.ProviderActivity;
import com.lll.beizertest.draw.DrawSignatureViewActivity;
import com.lll.beizertest.draw.DrawViewActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第二部分测试
 */
public class PartTwoActivity extends AppCompatActivity {

    @BindView(R.id.btn_provider)
    Button btnProvider;

    @BindView(R.id.btn_drawView)
    Button btnDrawView;

    @BindView(R.id.btn_nestedScrolling)
    Button btnNestedScrolling;

    @BindView(R.id.btn_customDrawView)
    Button btnCustomDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_two);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_provider, R.id.btn_drawView, R.id.btn_nestedScrolling,
            R.id.btn_customDrawView,R.id.btn_drawSignature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_provider: {
                Intent intent = new Intent(this, ProviderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawView: {
                Intent intent = new Intent(this, ScrollViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_nestedScrolling: {
                Intent intent = new Intent(this, NestedScrollingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_customDrawView: {
                Intent intent = new Intent(this, DrawViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawSignature:{
                Intent intent = new Intent(this, DrawSignatureViewActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

}
