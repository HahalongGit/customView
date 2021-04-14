package com.lll.beizertest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lll.beizertest.activity.NestedScrollingActivity;
import com.lll.beizertest.activity.ScrollViewActivity;
import com.lll.beizertest.activity.provider.ProviderActivity;
import com.lll.beizertest.activity.recycleview.DargRecycleViewActivity;
import com.lll.beizertest.databinding.ActivityPartTwoBinding;
import com.lll.beizertest.draw.DrawLayoutTestActivity;
import com.lll.beizertest.draw.DrawShaderTestActivity;
import com.lll.beizertest.draw.DrawSignatureViewActivity;
import com.lll.beizertest.draw.DrawViewActivity;
import com.lll.beizertest.draw.DrawXfermodeActivity;
import com.lll.beizertest.draw.XfermodeEraserViewActivity;
import com.lll.beizertest.router.RouterPathConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第二部分测试
 */
@Route(path = RouterPathConstants.ACTIVITY_URL_PART_TWO)
public class PartTwoActivity extends AppCompatActivity {

    @BindView(R.id.btn_provider)
    Button btnProvider;

    @BindView(R.id.btn_drawView)
    Button btnDrawView;

    @BindView(R.id.btn_nestedScrolling)
    Button btnNestedScrolling;

    @BindView(R.id.btn_customDrawView)
    Button btnCustomDrawView;

    @BindView(R.id.btn_drawShader)
    Button mBtnDrawShader;

    private BitmapRegionDecoder mBitmapRegionDecoder;

    private Bitmap mBitmap;

    private SharedPreferences mPreferences;

    private ActivityPartTwoBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPartTwoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ButterKnife.bind(this);
//        mBinding.btnCustomDrawView
        getClassLoader();
        mPreferences = getSharedPreferences("", MODE_PRIVATE);
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putInt("", 12);
        boolean commit = edit.commit();
        edit.apply();
    }

    @OnClick({R.id.btn_provider, R.id.btn_drawView, R.id.btn_nestedScrolling,
            R.id.btn_customDrawView, R.id.btn_drawSignature, R.id.btn_drawShader,
            R.id.btn_drawLayout, R.id.btn_drawXfermode, R.id.btn_drawEraser, R.id.btn_dragRecycleView})
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
            case R.id.btn_drawSignature: {
                Intent intent = new Intent(this, DrawSignatureViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawShader: {
                Intent intent = new Intent(this, DrawShaderTestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawLayout: {
                Intent intent = new Intent(this, DrawLayoutTestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawXfermode: {
                Intent intent = new Intent(this, DrawXfermodeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawEraser: {
                Intent intent = new Intent(this, XfermodeEraserViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_dragRecycleView: {
                Intent intent = new Intent(this, DargRecycleViewActivity.class);
                startActivity(intent);
            }
        }
    }

}
