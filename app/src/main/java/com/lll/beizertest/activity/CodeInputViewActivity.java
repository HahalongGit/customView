package com.lll.beizertest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;

/**
 * 下划线形式，验证码输入 demo
 * 主要思想：获取输入的长度，在View中绘制出 下划线，即将绘制的下划线绘制成光标选中的颜色，最后把输入的文字
 * 也绘制在下划线对应的位置上。（对于有些方框样子的验证码输入也是一样的）继承EditText 更方便处理。
 * 也可以继承View直接绘制。参考下面的代码
 * 参考 https://github.com/bajian/material-code-input
 *
 * 或者https://github.com/glomadrian/material-code-input
 */
public class CodeInputViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_input_view);
    }
}
