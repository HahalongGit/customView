package com.lll.beizertest.note_reflect.note;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by longlong on 2019/4/28.
 *
 * @ClassName: OnClick
 * @Description:
 * @Date 2019/4/28
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerType = View.OnClickListener.class, listenerSetter = "setOnClickListener", methodName = "onClick")
public @interface OnClick {
    int[] value();
}
