package com.lll.beizertest.skin.attr;

import android.view.View;

/**
 * Created by longlong on 2018/4/3.
 *
 * @ClassName: SkinType
 * @Description:
 * @Date 2018/4/3
 */

public enum  SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resourceName) {

        }
    },BACKGROUND("background") {
        @Override
        public void skin(View view, String resourceName) {

        }
    },SRC("src") {
        @Override
        public void skin(View view, String resourceName) {
            //获取资源设置
        }
    };

    /**
     * 根据名字掉对应的方法
     */
    private String mResName;

    SkinType(String resName){
        this.mResName = resName;
    }

    public abstract void skin(View view, String resourceName) ;
}
