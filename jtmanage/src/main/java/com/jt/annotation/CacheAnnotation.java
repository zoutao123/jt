package com.jt.annotation;

import com.jt.enu.KEY_ENUM;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/7/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheAnnotation {
    String value() default "";
    KEY_ENUM keyType() default KEY_ENUM.AUTO;
    int secondes() default 10;//缓存有效时间
}
