package com.lll.beizertest.note_reflect;

import android.app.Activity;
import android.view.View;

import com.lll.beizertest.note_reflect.note.ContentView;
import com.lll.beizertest.note_reflect.note.EventBase;
import com.lll.beizertest.note_reflect.note.ViewInfect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by longlong on 2019/4/28.
 *
 * @ClassName: ViewInfectUtils
 * @Description:
 * @Date 2019/4/28
 */

public class ViewInfectUtils {

    /**
     * 设置注解
     *
     * @param activity
     */
    public static void inject(Activity activity) {
        contentViewInject(activity);
        viewInject(activity);
        eventInfect(activity);
    }

    /**
     * 获取Activity的字段，设置值
     *
     * @param activity
     */
    private static void viewInject(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            ViewInfect annotation = field.getAnnotation(ViewInfect.class);
            if (annotation != null) {
                int viewId = annotation.value();
                if (viewId != -1) {
                    try {
                        Method method = clazz.getMethod("findViewById", int.class);//获取方法
                        try {
                            Object resView = method.invoke(activity, viewId);
                            field.setAccessible(true);
                            field.set(activity, resView);//要改变那个类对象的那个字段的值 the object whose field should be modified
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void contentViewInject(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView annotation = clazz.getAnnotation(ContentView.class);
        if (annotation != null) {
            int contentViewLayoutId = annotation.value();
            if (contentViewLayoutId != -1) {
                try {
                    Method method = clazz.getMethod("setContentView", int.class);
                    method.setAccessible(true);
                    method.invoke(activity, contentViewLayoutId);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * onClikc事件监听
     * 监听事件的实现参考 https://blog.csdn.net/lmj623565791/article/details/39275847# 鸿洋
     *
     * @param activity
     */
    private static void eventInfect(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();// 获取所有的注解
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();// 获取注解的类型
                EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);// EventBase注解主要是获取设置的类型
                if (eventBaseAnnotation != null) {
                    String listenerSetter = eventBaseAnnotation.listenerSetter();// setOnClickListener
                    Class<?> listenerType = eventBaseAnnotation.listenerType();// View.OnClickListener.class
                    String methodName = eventBaseAnnotation.methodName();// 方法名称onClick

//                    try {
//                        Method method1 = annotationType.getDeclaredMethod("value");
//                        try {
//                            int viewIds[] = (int[]) method1.invoke(annotation, null);
//                            // 通过DynamicHandler 和Proxy获取监听器的代理对象。？？？？
//                            DynamicHandler dynamicHandler = new DynamicHandler(activity);
//                            dynamicHandler.addMethod(methodName, method);
//                            Object listener = Proxy.newProxyInstance(
//                                    listenerType.getClassLoader(),
//                                    new Class<?>[]{listenerType}, dynamicHandler); // listenerType 就是监听的是那个类的 View.OnClickListener.class
//                            //遍历所有的View，通过反射设置事件
//                            for (int viewId : viewIds) {
//                                View view = activity.findViewById(viewId);
//                                Method setEventListenerMethod = view.getClass()
//                                        .getMethod(listenerSetter, listenerType);//获取onClick
//                                setEventListenerMethod.invoke(view, listener);//设置onClick
//                            }
//
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    }

                }
            }
        }
    }

}
