package com.jt.util;

import com.jt.pojo.User;

/**
 * Created by Administrator on 2019/7/19.
 */
public class UserThreadLocal {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user){

        threadLocal.set(user);
    }

    public static User get(){

        return threadLocal.get();
    }

    //防止内存泄漏
    public static void remove(){

        threadLocal.remove();
    }
}
