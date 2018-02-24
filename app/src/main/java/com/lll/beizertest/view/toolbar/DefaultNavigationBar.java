package com.lll.beizertest.view.toolbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lll.beizertest.R;


/**
 * Created by longlong on 2018/2/24.
 *
 * @ClassName: DefaultNavigationBar
 * @Description: 设置默认实现的导航栏（业务不一样，样子不一样）
 * @Date 2018/2/24
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationBarParam> {


    private DefaultNavigationBar(Builder.DefaultNavigationBarParam params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar_layout;
    }

    @Override
    public void applyView() {
        //绑定View的不同的效果
        setText(R.id.tv_title,getParams().mTitle);
        setText(R.id.tv_rightText,getParams().mRightText);
        setOnClickListener(R.id.tv_rightText,getParams().mRightClickListener);
        setOnClickListener(R.id.iv_left,getParams().mLeftClickListener);
        //一般正常 左边的是默认的关闭
        //右边不一定添加点击
    }

    /**
     * 构造者
     */
    public static class Builder extends AbsNavigationBar.Bulider{

        private DefaultNavigationBarParam P;

        //1.设置所有的效果

        public Builder setTitle(String title){
            P.mTitle = title;
            return this;
        }

        public Builder setRightText(String rightText){
            P.mRightText = rightText;
            return this;
        }

        public Builder setRightClickListener(View.OnClickListener onClickListener){
            P.mRightClickListener = onClickListener;
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener onClickListener){
            P.mRightClickListener = onClickListener;
            return this;
        }

        /**
         * 构造Builder
         * @param context
         * @param parent 添加导航到 一个指定布局之前
         */
        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationBarParam(context,parent);
        }

        /**
         * 构造Builder 添加导航栏到系统布局上
         * @param context
         */
        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationBarParam(context,null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar(P);
            return defaultNavigationBar;
        }

        /**
         * 参数集类
         */
        public static class DefaultNavigationBarParam extends AbsNavigationBar.Bulider.AbsNavigationBarParam{

            /**
             * 标题
             */
            public String mTitle;

            /**
             * 右边文字
             */
            public String mRightText;

            /**
             * 点击事件
             */
            public View.OnClickListener mRightClickListener;

            public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) mContenxt;
                    activity.finish();
                }
            };
            //其他的效果..

            //2.所有的效果（集合）
            public DefaultNavigationBarParam(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }

    }
}
