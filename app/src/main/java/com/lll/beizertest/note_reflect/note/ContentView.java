package com.lll.beizertest.note_reflect.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by longlong on 2019/4/28.
 *
 * @ClassName: ContentView
 * @Description: Runtime 的注解会保留到编译，最后加载到jvm运行
 *                 class 类型的注解会保留到编译class 生成阶段
 *                 source 只会保留在源码阶段，不会参与编译。
 * @Date 2019/4/28
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {
    int value();
}
