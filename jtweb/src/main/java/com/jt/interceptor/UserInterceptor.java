package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring4中必须重写HandlerInterceptor的三个方法
 * spring5中HandlerInterceptor有默认值
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * boolean：
     * true: 表示放行
     * false:表示拦截  配合重定向使用
     * 业务需求:
     * 1.获取用户Cookie中的token信息
     * 2.校验数据是否有效
     * 3.校验redis中是否有数据
     * 如果上述操作正确无误 返回true
     * 否则return false 重定向到登录页面
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取cookie数据
        Cookie[] cookies = request.getCookies();
        if (cookies.length != 0) {
            String token = null;
            for (Cookie cookie : cookies) {
                if ("JT_TICKET".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
            //2.校验token的有效性
            if (!StringUtils.isEmpty(token)) {
                //2.1校验Redis中是否有数据
                String userJson = jedisCluster.get(token);
                if (!StringUtils.isEmpty(userJson)) {
                    User user = ObjectMapperUtil
                            .toObject(userJson, User.class);
                    System.out.println(user);
                    System.out.println(user.getId());
                    //利用threadlocal实现数据共享
                    UserThreadLocal.set(user);
                    return true;
                }
            }
        }
        //必须重定向
        response.sendRedirect("/user/login.html");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
