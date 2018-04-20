package com.lll.imageselectlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

/**
 * 自定义图片选择器
 */
public class ImageSelectActivity extends AppCompatActivity {


    private final  int MODEL_MULTI = 0x0011;

    private final  int MODEL_SINGLE = 0x0012;

    //是否可以显示相机的key
    private final String  EXTRA_SHOW_CAMERA =  "EXTRA_SHOW_CAMERA";
    //总共可以显示多少图片的key
    private final String  EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    //原始图片路径
    private final String  EXTRA_DEFAULT_SELECTED_LIST = "EXTRA_DEFAULT_SELECTED_LIST";
    //选择模式的key
    private final String  EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    //返回图片选择列表的KEY
    private final String  EXTRA_RESULT = "EXTRA_RESULT";

    //单选或者多选
    private int mMode = MODEL_MULTI;

    private int mMaxCount = 8;

    private boolean mShowCamera;

    private Button mCompleteSelectBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }


}
