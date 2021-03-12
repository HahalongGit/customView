package com.lll.beizertest.router;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * ARouter 测试拦截器
 * 拦截器会自动执行，
 *
 * @author RunningDigua
 * @date 2021/1/19
 */

@Interceptor(priority = 8, name = "一个默认的拦截器")
public class TestInterceptor implements IInterceptor {

    private static final String TAG = "TestInterceptor";

    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.e(TAG, "process-thread:-" + Thread.currentThread());
        Log.e(TAG, "process-postcard:-" + postcard.getName() +"--"+ postcard.getPath());
        if (true) {
            callback.onContinue(postcard);  // 处理完成，交还控制权
        } else {
            callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程/**/
        }

        // 以上两种至少需要调用其中一,否则不会继续路由
    }

    @Override
    public void init(Context context) {
        Log.e(TAG, "init-拦截器-");
        mContext = context;
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    }
}
