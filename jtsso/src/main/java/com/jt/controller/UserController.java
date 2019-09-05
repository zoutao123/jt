package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.UserService;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 经过跨域请求 返回系统数据data:true(已存在)/false(不存在)
     *
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param,
                                 @PathVariable Integer type,
                                 String callback) {
        JSONPObject object = null;
        try {
            //查询数据库,检查数据是否存在
            boolean flag = userService.findUserCheck(param, type);
            object = new JSONPObject(callback, SysResult.success(flag));
        } catch (Exception e) {
            e.printStackTrace();
            object = new JSONPObject(callback, SysResult.fail());
        }
        return object;
    }

    @RequestMapping("/query/{token}")
    public JSONPObject findUserByToken(String callback,
                                       @PathVariable String token){
        //根据秘钥查询用户信息
        String userJson = jedisCluster.get(token);
        JSONPObject jsonpObject = null;
        if (StringUtils.isEmpty(userJson)){
            jsonpObject = new JSONPObject(callback,SysResult.fail());
        }else {
            jsonpObject = new JSONPObject(callback,SysResult.success(userJson));
        }
        return jsonpObject;
    }
}
