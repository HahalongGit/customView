package com.lll.beizertest.activity;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试皮肤
 */
public class SkinActivity extends AppCompatActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"文件名";//下载的皮肤的包文件路径，打包一个apk,给任意的文件名
        //我们查看系统的资源是如何加载的？然后知道了,Resources 的操作。
        Resources resources = getResources();
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);
            method.invoke(assetManager,path);//调用addAssetPath 方法获取资源

            //自己创建一个Resource 获取资源
            Resources newResource = new Resources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());
            int drawabeId = newResource.getIdentifier("资源名称","drawable","资源所在包名");//采用这个方法获取资源id
            Drawable drawable = newResource.getDrawable(drawabeId);//获取资源
            //这样就可以获取到资源，然后设置到文件
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
