package com.lll.beizertest.activity.hook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * test hook listener
 */
public class HookListenerActivity extends AppCompatActivity {

    private static final String TAG = "HookListenerActivity";
    @BindView(R.id.btn_hookListener)
    Button btnHookListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_listener);
        ButterKnife.bind(this);
        hookListener(btnHookListener);

    }

    /**
     * 采用静态代理的方式hook
     *
     * @param button
     */
    private void hookListener(View button) {

        try {
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");//这个方法不可见，反射获取
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(button);//调用button对象的getListenerInfo这个方法执行结果 获取到listenerInfo对象，这个对象包含clickListener
            try {
                Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");//获取这个类ListenerInfo
                try {
                    Field mOnClickListener = listenerInfoClass.getDeclaredField("mOnClickListener");//获取字段
                    mOnClickListener.setAccessible(true);
                    View.OnClickListener onClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);//获取listenerInfo 中的mOnClickListener
                    // 相当于View.OnClickListener onClickListener = listenerInfo.getMOnClickListener();
                    HookOnClickListener hookOnClickListener = new HookOnClickListener(onClickListener);// 替换 代理onClickListener
                    mOnClickListener.set(listenerInfo, hookOnClickListener);
                    //把hookOnClickListener设置到listenerInfo这个对象中，相当于 getListenerInfo().mOnClickListener = l;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.btn_hookListener)
    public void onViewClicked(View view) {
        // Button的点击事件
        Log.e(TAG, "自己写的btn的点击事件");
    }
// 运行结果如下：
//E/HookListenerActivity: 调用系统的onclick前
//E/HookListenerActivity: 自己写的btn的点击事件
//E/HookListenerActivity: 调用系统的onclick之后

    /**
     * 静态代理实现
     */
    static class HookOnClickListener implements View.OnClickListener {

        private View.OnClickListener listener;

        public HookOnClickListener(View.OnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            Log.e(TAG, "调用系统的onclick前");
            if (listener != null) {
                listener.onClick(v);
            }
            Log.e(TAG, "调用系统的onclick之后");
        }
    }

}
