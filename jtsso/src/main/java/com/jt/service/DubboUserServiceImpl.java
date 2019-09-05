package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

/**
 * Created by Administrator on 2019/7/18.
 */
//我是提供者的实现类
@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService{

    private UserMapper userMapper;

    private JedisCluster jedisCluster;

    @Override
    public void saveUser(User user) {
        //1.密码加密
        String md5Password =
                DigestUtils.md5DigestAsHex
                        (user.getPassword().getBytes());
        //2.封装数据
        user.setPassword(md5Password)
                .setEmail(user.getPhone())
                .setCreated(new Date())
                .setUpdated(new Date());
        userMapper.insert(user);
    }

    /**
     * 1.用户信息校验
     *   密码加密处理之后查询数据库
     * @param user
     * @return
     */
    @Override
    public String doLogin(User user) {
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        //根据对象中不为null的属性当做where条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User userDB = userMapper.selectOne(queryWrapper);
        String token = null;

        if (userDB!=null){
            //1.将用户的数据保存到redis中 生成key
            String tokenTemp =
                    "JT_TICKET_"+System.currentTimeMillis()
                            +user.getUsername();
            tokenTemp = DigestUtils.md5DigestAsHex
                    (tokenTemp.getBytes());

            //2.生成value数据 userJson
            //为了安全 需要将数据进行脱敏处理
            userDB.setPassword("123456你猜你妈呢??");
            String userJson = ObjectMapperUtil.toJson(userDB);

            jedisCluster.setex(tokenTemp,7*24*3600,userJson);
            token = tokenTemp;
        }
        return token;
    }


}
