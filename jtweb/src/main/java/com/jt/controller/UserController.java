package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2019/7/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 利用dubbo注解注入对象
     */
    @Reference(timeout = 3000,check = false)
    private DubboUserService userService;

    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping("/{moduleName}")
    public String moduleName(@PathVariable String moduleName){
        return moduleName;
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user){
        //利用dubbo rpc协议完成远程过程调用
        userService.saveUser(user);
        return SysResult.success();
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletResponse response){
        String token = userService.doLogin(user);
        //校驗遠程服務器返回數據是否為null
        if(StringUtils.isEmpty(token)){
            return SysResult.fail();
        }
        //将token数据写入cookie
        Cookie cookie = new Cookie("JT_TICKET", token);
        /**
         * setMaxAge(-1) 表示会话关闭cookie删除
         * setMaxAge(0)  表示删除cookie
         */
        cookie.setMaxAge(7*24*3600);
        //表示cookie的使用权限
        cookie.setPath("/");
        //设定cookie共享
        /**
         *规定:每一个网址都有自己固定的cookie信息 默认不共享
         *需求:要求在一级域名与二级域名之间
         * 可以设定domain标签实现cookie共享
         *
         */
        cookie.setDomain("jt.com");
        response.addCookie(cookie);
        return SysResult.success();
    }

    @RequestMapping("/logout")
    public String doLogout(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies.length!=0){
            String token = null;
            for (Cookie cookie:cookies){
                if ("JT_TICKET".equals(cookie.getName())){
                    token = cookie.getValue();
                    break;
                }
            }
            //判断token数据有值
            if (!StringUtils.isEmpty(token)){
                jedisCluster.del(token);
                Cookie cookie = new Cookie("JT_TICKET", "");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setDomain("jt.com");
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
    }
}
