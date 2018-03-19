package com.lll.beizertest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.view.pictureselector.FullyGridLayoutManager;
import com.lll.beizertest.view.pictureselector.GridImageAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图片选择库 PictureSelector 测试
 * 参考：https://github.com/LuckSiege/PictureSelector
 * 参考项目中图片裁剪使用了：https://github.com/Yalantis/uCrop
 */
public class PictureSelectorActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.iv_selectImage)
    ImageView ivSelectImage;
    @BindView(R.id.btn_takePicture)
    Button btnTakePicture;
    @BindView(R.id.btn_selectPicture)
    Button btnSelectPicture;

//    private int chooseMode = PictureMimeType.ofAll();
    private int chooseMode = PictureMimeType.ofAll();

    private int themeId = R.style.picture_default_style;

    private List<LocalMedia> selectList = new ArrayList<>();

    private GridImageAdapter imageAdapter;

    private FullyGridLayoutManager fullyGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);
        ButterKnife.bind(this);
        fullyGridLayoutManager = new FullyGridLayoutManager(this,4, GridLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(fullyGridLayoutManager);
        imageAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                Toast.makeText(PictureSelectorActivity.this, "添加图片", Toast.LENGTH_SHORT).show();
            }
        });
        imageAdapter.setList(selectList);
        recycler.setAdapter(imageAdapter);
    }

    @OnClick({R.id.btn_takePicture, R.id.btn_selectPicture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_takePicture:
                // 单独拍照
                PictureSelector.create(PictureSelectorActivity.this)
                        .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .enableCrop(true)// 是否裁剪
                        .compress(true)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .selectionMedia(selectList)// 是否传入已选图片
                        .withAspectRatio(1,1)
                        .rotateEnabled(false)
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .scaleEnabled(true)// 裁剪是否可放大缩小图片
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
            case R.id.btn_selectPicture:
                // .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                // 单独拍照
                PictureSelector.create(PictureSelectorActivity.this)
                        .openGallery(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(themeId)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(9)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .selectionMode(true ?
                                PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                        .previewImage(false)// 是否可预览图片
                        .previewVideo(false)// 是否可预览视频
                        .enablePreviewAudio(false) // 是否可播放音频
                        .isCamera(false)// 是否显示拍照按钮
                        .enableCrop(true)// 是否裁剪
                        .compress(true)// 是否压缩
                        .glideOverride(260, 260)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false ? false : true)// 是否显示uCrop工具栏，默认不显示
                        .isGif(false)// 是否显示gif图片
                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(true)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        .scaleEnabled(true)// 裁剪是否可放大缩小图片
                        .rotateEnabled(false) // 裁剪是否可旋转图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:{
                    selectList = PictureSelector.obtainMultipleResult(data);
                    imageAdapter.setList(selectList);
                    imageAdapter.notifyDataSetChanged();
                    for (LocalMedia localMedia : selectList) {
                        Log.e("TAG","localMedia-Path:"+localMedia.getPath());
                    }
                    break;
                }
            }
        }
    }
}
