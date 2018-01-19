package com.lll.beizertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lll.beizertest.R;
import com.lll.beizertest.view.bannerViewPager.BannerAdapter;
import com.lll.beizertest.view.bannerViewPager.BannerView;
import com.lll.beizertest.view.bannerViewPager.BannerViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BannerViewPagerActivity extends AppCompatActivity {


    @BindView(R.id.bannerView)
    BannerView bannerView;
    @BindView(R.id.btn_starRoll)
    Button btnStarRoll;

    private int ImageRes[] = new int[]{R.mipmap.bg_puple, R.mipmap.bg_pink, R.mipmap.bg_orange, R.mipmap.bg_blue};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_view_pager);
        ButterKnife.bind(this);
        bannerView.setBannerAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView bannerIv = new ImageView(BannerViewPagerActivity.this);
                bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
                //int a = position%2;
                bannerIv.setImageResource(ImageRes[position%ImageRes.length]);//这里设置没问题，不影响速度
                return bannerIv;
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
    }

    @OnClick(R.id.btn_starRoll)
    public void onViewClicked() {
        bannerView.starRoll();
    }
}
