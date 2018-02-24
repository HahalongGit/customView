package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;

import com.lll.beizertest.R;
import com.lll.beizertest.view.expendtext.ExpandableTextView;

public class ExpendTextViewActivity extends AppCompatActivity {

    private ExpandableTextView expandableTextView;

    private  SparseBooleanArray mCollapsedStatus;
    private  String[] sampleStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expend_text_view);
        mCollapsedStatus = new SparseBooleanArray();
        sampleStrings = getResources().getStringArray(R.array.sampleStrings);
        expandableTextView = findViewById(R.id.expand_text_view);
        expandableTextView.setText(sampleStrings[0], mCollapsedStatus, 0);
    }
}
