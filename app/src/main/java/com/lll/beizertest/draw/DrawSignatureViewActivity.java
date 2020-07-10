package com.lll.beizertest.draw;

import android.os.Bundle;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.R;
import com.lll.beizertest.draw.view.SignatureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手写绘制
 */
public class DrawSignatureViewActivity extends AppCompatActivity {

    @BindView(R.id.btn_clearSign)
    Button mBtnClearSign;
    @BindView(R.id.btn_saveSign)
    Button mBtnSaveSign;
    @BindView(R.id.guideline4)
    Guideline mGuideline4;
    @BindView(R.id.signatureView)
    SignatureView mSignatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_signature_view);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_clearSign, R.id.btn_saveSign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clearSign: {
                mSignatureView.clearSign();
                break;
            }
            case R.id.btn_saveSign: {

                break;
            }
        }
    }
}
