package com.lll.beizertest.note_reflect.note;

import android.os.Environment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by longlong on 2019/4/28.
 *
 * @ClassName: ViewInfect
 * @Description: 自定义注解
 * @Date 2019/4/28
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ViewInfect {
    int value();
}
