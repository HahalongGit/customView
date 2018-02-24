package com.lll.beizertest.view.toolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longlong on 2018/2/24.
 *
 * @ClassName: AbsNavigationBar
 * @Description:
 * @Date 2018/2/24
 */

public class AbsNavigationBar implements INavigationBar {

    private Bulider.AbsNavigationBarParam mParams;

    public AbsNavigationBar(Bulider.AbsNavigationBarParam params) {
        this.mParams = params;
        createAndBindView();
    }

    /**
     * 创建View 和绑定
     */
    private void createAndBindView() {
        //加载布局
        View navigation = LayoutInflater.from(mParams.mContenxt).inflate(bindLayoutId(),mParams.mParent,false);//false的区别？
        mParams.mParent.addView(navigation,0);
        applyView();//绑定参数
    }

    @Override
    public int bindLayoutId() {

        return 0;
    }

    @Override
    public void applyView() {

    }

    /**
     * Builder
     */
    public abstract static class Bulider{

        private AbsNavigationBarParam P;

        /**
         * 构造Builder
         * @param context
         * @param parent
         */
        public Bulider(Context context, ViewGroup parent) {
            P = new AbsNavigationBarParam(context,parent);
        }
        /**
         *
         */
        public abstract void builder();

        /**
         * 参数集
         */
        public static class AbsNavigationBarParam{

            public Context mContenxt;

            public ViewGroup mParent;

            public AbsNavigationBarParam(Context context, ViewGroup parent) {
                this.mContenxt = context;
                this.mParent = parent;
            }
        }

    }

}
