package com.lll.beizertest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lll.beizertest.R;
import com.lll.beizertest.skin.support.SkinCompatViewInflater;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试皮肤 ，复制的代码不兼容--》SkinCompatViewInflater 后续的没有写。需要重新学习这部分。
 */
public class SkinActivity extends Activity implements LayoutInflaterFactory {

    Button btnChangeSkin;
    private String path;

    private SkinCompatViewInflater mAppCompatViewInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setFactory();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        btnChangeSkin = findViewById(R.id.btn_changeSkin);
        //setSkin();

    }

    /**
     * 实现Factory 的 创建View方法
     * @param parent
     * @param name
     * @param context
     * @param attrs
     * @return
     */
//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        //View view =  createView(parent, name, context, attrs);
//        //return super.onCreateView(parent, name, context, attrs);
//        return view;
//    }

//    @Override
//    public View createView(View parent, final String name, @NonNull Context context,
//                           @NonNull AttributeSet attrs) {
//        if (mAppCompatViewInflater == null) {
//            mAppCompatViewInflater = new SkinCompatViewInflater();
//        }
//
//        boolean inheritContext = false;
//        if (IS_PRE_LOLLIPOP) {
//            inheritContext = (attrs instanceof XmlPullParser)
//                    // If we have a XmlPullParser, we can detect where we are in the layout
//                    ? ((XmlPullParser) attrs).getDepth() > 1
//                    // Otherwise we have to use the old heuristic
//                    : shouldInheritContext((ViewParent) parent);
//        }
//
//        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
//                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
//                true, /* Read read app:theme as a fallback at all times for legacy reasons */
//                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
//        );
//    }

    /**
     * 设置一个Factory 拦截View的创建 （换肤功能分析 中有讲解）
     */
    private void setFactory() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        //layoutInflater.inflate();
        LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                Log.e("TAG", "拦截View1");
                //1.创建View
                //2.解析属性 src banckround,textColor 等。以及自定义属性
                //3.同意交给SkinManager
                if (name.equals("Button")) {//拦截Button创建
                    TextView textView = new TextView(SkinActivity.this);
                    textView.setText("拦截的View1");
                    return textView;
                }
                return null;
            }

            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e("TAG", "拦截View2");
                if (name.equals("Button")) {
                    TextView textView = new TextView(SkinActivity.this);
                    textView.setText("拦截的View2");
                    //执行了这里
                    return textView;
                }
                return null;
            }
        });
    }

    private void setSkin() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "文件名";//下载的皮肤的包文件路径，打包一个apk,给任意的文件名
        //我们查看系统的资源是如何加载的？然后知道了,Resources 的操作。
        Resources resources = getResources();
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(assetManager, path);//调用addAssetPath 方法获取资源

            //自己创建一个Resource 获取资源
            Resources newResource = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
            int drawabeId = newResource.getIdentifier("资源名称", "drawable", "资源所在包名");//采用这个方法获取资源id
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
