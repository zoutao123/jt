package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.pojo.User;

import javax.servlet.http.Cookie;

/**
 * 定义中立的第三方接口
 */

public interface DubboUserService {

    /**
     * 完成用户的入库操作
     * @param user
     */
    void saveUser(User user);

    String doLogin(User user);


}
