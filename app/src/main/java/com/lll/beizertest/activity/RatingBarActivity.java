package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.view.RatingBar;

public class RatingBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnClickStarListener(new RatingBar.OnClickStarListener() {
            @Override
            public void onClickStar(int star) {
                Toast.makeText(RatingBarActivity.this, "star:" + star, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
