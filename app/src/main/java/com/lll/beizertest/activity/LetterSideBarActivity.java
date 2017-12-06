package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;
import com.lll.beizertest.view.LetterSideBar;

public class LetterSideBarActivity extends AppCompatActivity {

    private LetterSideBar letterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_side_bar);
        letterSideBar = findViewById(R.id.letterSideBar);

    }
}
