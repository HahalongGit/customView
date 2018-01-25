package com.lll.beizertest.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by hcDarren on 2017/7/9.
 */

public class StatusBarUtil {
    /**
     * 为我们的 activity 的状态栏设置颜色
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity,int color){
        // 5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 直接调用系统提供的方法 setStatusBarColor
            activity.getWindow().setStatusBarColor(color);
        }
        // 4.4 - 5.0 之间  采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 首先把他弄成全屏（），在状态栏的部分加一个布局
            // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // 电量 时间 网络状态 都还在
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 在状态栏的部分加一个布局 setContentView 源码分析，自己加一个布局 (高度是状态栏的高度)
            View view = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);

            //  android:fitsSystemWindows="true" 每个布局都要写
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            // 获取activity中setContentView布局的根布局
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setPadding(0,getStatusBarHeight(activity),0,0);
            // View activityView = contentView.getChildAt(0);
            // activityView.setFitsSystemWindows(true);
            // activityView.setPadding(0,getStatusBarHeight(activity),0,0);
        }
    }

    /**
     * 获取状态栏的高度
     * @param activity
     * @return
     */
    private static int getStatusBarHeight(Activity activity) {
        // 插件式换肤：怎么获取资源的，先获取资源id，根据id获取资源
        Resources resources = activity.getResources();
        int statusBarHeightId = resources.getIdentifier("status_bar_height","dimen","android");
        Log.e("TAG",statusBarHeightId+" -> "+resources.getDimensionPixelOffset(statusBarHeightId));
        return resources.getDimensionPixelOffset(statusBarHeightId);
    }

    /**
     * 设置activity全屏
     * @param activity
     */
    public static void setActivityTranslucent(Activity activity){
        // 5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 这个怎么写有没有思路？看源码  29次
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // 4.4 - 5.0 之间  采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
