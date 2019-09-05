package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/7/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) //注解的作用范围
public @interface Cache_Update {
}
