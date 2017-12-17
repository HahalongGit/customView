package com.lll.beizertest.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;



/**
 * Created by longlong on 2017/9/28.
 *
 * @ClassName: CommonUtil
 * @Description:
 * @Date 2017/9/28
 */

public class CommonUtil {

    private static final String TAG = "CommonUtil";

    /**
     * 验证邮箱的正则
     */
    public static String EMAIL_REX = "^[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}$";

    /*
	 * 判断内容是否为邮?
	 *
	 * @param paramString
	 * @return boolean
	 */
    public static boolean isValidEmail(String paramString) {
        if (paramString!=null && paramString.matches(EMAIL_REX)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串中是否包含汉字
     * @param inputStr
     * @return true 包含
     */
    public static boolean isContainCharacter(String inputStr){
        int nums = 0;
        Log.e("TAG","inputStr.Length:"+inputStr.length());
        for(int i =0;i<inputStr.length();i++){
            if( inputStr.substring(i,i+1).matches("^[\\u4e00-\\u9fa5]{0,}$")){
                nums++;
            }
        }
        Log.e("TAG","nums结果:"+nums);
        if(nums>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取应用设置详情页面intent
     * @param context
     * @return
     */
    public static void getAppDetailSettingIntent(Context context) {
        if(context!=null){
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
//                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
            context.startActivity(localIntent);
        }else{
            new IllegalArgumentException("content can not null");
        }
    }

    //            Uri uri = Uri.fromParts(
//                    getString(R.string.permission_package),
//                    RealNameaVerifyActivity.this.getPackageName(),
//                    null);
//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            intent.setData(uri);
//            startActivityForResult(intent, TAKE_PICTURE_PERMISSION_CODE);

}
