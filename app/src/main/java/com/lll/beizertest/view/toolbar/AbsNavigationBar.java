package com.lll.beizertest.view.toolbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by longlong on 2018/2/24.
 *
 * @ClassName: AbsNavigationBar
 * @Description:
 * @Date 2018/2/24
 */

public  abstract class AbsNavigationBar<P extends AbsNavigationBar.Bulider.AbsNavigationBarParam> implements INavigationBar {

    /**
     * 设置泛型的P
     */
    private P mParams;

    private View navigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }


    /**
     * 设置添加点击
     * @param viewId
     * @param clickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = navigationView.findViewById(viewId);
        if(clickListener!=null){
            view.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置Text
     * @param viewId
     * @param text
     */
    public void setText(int viewId, String text) {
        TextView textView = navigationView.findViewById(viewId);
        if(!TextUtils.isEmpty(text)){
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }

    /**
     * 创建View 和绑定(相当于 Activity中 findViewById 然后设置View的数据)
     */
    private void createAndBindView() {
        //加载布局
        if(mParams.mParent==null){//用户不设置根布局的id时，通过获取系统布局添加这个导航栏
            Activity activity = (Activity) mParams.mContenxt;
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
           mParams.mParent = (ViewGroup) viewGroup.getChildAt(0);
        }
        if(mParams.mParent==null){//如果还是null 直接返回
            return;
        }
        navigationView = LayoutInflater.from(mParams.mContenxt).inflate(bindLayoutId(),mParams.mParent,false);//false的区别？
        mParams.mParent.addView(navigationView,0);
        applyView();//绑定参数
    }

    /**
     * Builder
     */
    public abstract static class Bulider{

        //基类不写示例
        //private AbsNavigationBarParam P;

        /**
         * 构造Builder
         * @param context
         * @param parent
         */
        public Bulider(Context context, ViewGroup parent) {
            //P = new AbsNavigationBarParam(context,parent);
        }

        /**
         * 创建一个导航栏
         */
        public abstract AbsNavigationBar builder();

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
