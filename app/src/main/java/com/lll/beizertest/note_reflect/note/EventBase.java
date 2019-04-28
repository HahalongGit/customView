package com.lll.beizertest.note_reflect.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by longlong on 2019/4/28.
 *
 * @ClassName: EventBase
 * @Description:
 * @Date 2019/4/28
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {
    Class<?> listenerType();

    String listenerSetter();

    String methodName();
}
