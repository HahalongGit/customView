package com.lll.beizertest.note_reflect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.note_reflect.note.ContentView;
import com.lll.beizertest.note_reflect.note.OnClick;
import com.lll.beizertest.note_reflect.note.ViewInfect;


/**
 * 自定义注解
 */
@ContentView(R.layout.activity_note_reflect)
public class NoteReflectActivity extends AppCompatActivity {

    @ViewInfect(R.id.tv_noteTest)
    TextView tvNoteTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_note_reflect);
        ViewInfectUtils.inject(this);

        tvNoteTest.setText("设置注解获取后的TextView");
    }


    @OnClick(R.id.btn_viewInfect)
    public void clickBtnInvoked(View view) {
        switch (view.getId()) {
            case R.id.btn_viewInfect: {
                Toast.makeText(this, "点击注解Button", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
