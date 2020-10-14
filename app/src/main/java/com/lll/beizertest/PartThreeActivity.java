package com.lll.beizertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lll.beizertest.proxy.HelloService;
import com.lll.beizertest.proxy.HelloServiceImpl;
import com.lll.beizertest.proxy.HelloServiceProxy;

import java.lang.reflect.Proxy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PartThreeActivity extends AppCompatActivity {

    private static final String TAG = "PartThreeActivity";

    @BindView(R.id.btn_dynamicProxy)
    Button mBtnDynamicProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_three);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_dynamicProxy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dynamicProxy: {
                testDynamic();
                break;
            }
        }
    }

    public void testDynamic() {
        HelloService service = new HelloServiceImpl();
        HelloServiceProxy helloServiceProxy = new HelloServiceProxy(service);//传递一个被代理对象到Handler中
        //创建的实例对象
        Log.e(TAG, "service-name: " + service.getClass().getName());// 输出结果：com.lll.beizertest.proxy.HelloServiceImpl
        //创建的代理对象，三个参数：被代理的类加载器，被代理的类的接口集合，InvocationHandler处理对象
        HelloService proxyService = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(),
                new Class<?>[]{HelloService.class},helloServiceProxy);
        Log.e(TAG, "proxyService-name: " + proxyService.getClass().getName());//输出结果：$Proxy0
        proxyService.helloWorld("丽丽好~");
        // TODO: [RunningDigua create at 2020/10/14]
        //已经创建了HelloService实例，为什么还要用newProxyInstance 来在创建一次？？

        //通过newProxyInstance创建的代理对象是在jvm中运行时动态生成的一个对象，
        // 它不是我们的InvcationHandler类型，也不是我们定义的那组接口类型，而是在
        //运行时动态生成的一个对象，并且它的命名方式都是以$开头Proxy中最后一个
        // 数字表示对象的标号
        // 我们看到，在真正的通过代理对象来调用真实对象的方法的时候，我们可以在改方法执行的前后添加
        // 自己的操作，同时我们可以看到，我们这个method方法是这样的：
        //Method：public abstract void com.lll.beizertest.proxy.HelloService.helloWorld(java.lang.String)

        //这个方法就是Service接口中的方法，这也就证明了当我们通过代理对象来调用对象方法的
        // 时候其实际就是委托其关联到handler对象的invoke方法中来调用的，并不是自己来真实
        // 调用的，而是通过代理方式来调用的。
        //这就是java的动态代理机制。
    }

}
