package com.jt.aspect;


import com.jt.annotation.CacheAnnotation;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyTree;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/13.
 */
@Aspect
@Service
@Slf4j
public class CacheAspect {
    /**
     * 环绕通知必须使用ProceedingJoinPoint
     * 通知中的参数ProceedingJoinPoint，必须位于第一位
     */

    @Autowired(required = false)
    private JedisCluster jedisCluster;
//    @Around("@annotation(com.jt.annotation.CacheAnnotation)")
//    public List<EasyTree> around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        Class<?> Tarmage = joinPoint.getTarget().getClass();
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        Object[] args = joinPoint.getArgs();
//        String operation="";
//        Method method = Tarmage.getMethod(signature.getName(), (Class<?>[]) args);
//        CacheAnnotation annotation = method.getDeclaredAnnotation(CacheAnnotation.class);
//        operation = annotation.value();
//        if (operation.equals("update")){
//
//        }
//        String key = "ITEM_CACHE_"+args[0];
//        String result = jedisCluster.get(key);
//        List<EasyTree> list = new ArrayList<>();
//        if (StringUtils.isEmpty(result)){
//            List<EasyTree> proceed = (List<EasyTree>)joinPoint.proceed();
//            String toJson = ObjectMapperUtil.toJson(proceed);
//            jedisCluster.set(key,toJson);
//            return proceed;
//        }
//        List list1 = ObjectMapperUtil.toObject(result, list.getClass());
//        return list1;
//    }

    @Around("@annotation(cache_find)")
    public Object around1(ProceedingJoinPoint joinPoint,
                          CacheAnnotation cache_find) throws Throwable {
        //获取key的值
        String key;
        KEY_ENUM key_enum = cache_find.keyType();
        if (key_enum.equals(KEY_ENUM.AUTO)){
            key = joinPoint.getSignature().getName()+"::"+
                    joinPoint.getArgs()[0];
        }else {
            key = cache_find.value();
        }
        String result = jedisCluster.get(key);
        Object data = null;
        if (StringUtils.isEmpty(result)){
            data = joinPoint.proceed();
            String json = ObjectMapperUtil.toJson(data);
            System.out.println("从数据库取值");
            if (cache_find.secondes()==0){
                //没有超时时间
                jedisCluster.set(key,json);
            }else {
                jedisCluster.setex(key,cache_find.secondes(),json);
            }
        }else {
            MethodSignature signature =
                    (MethodSignature) joinPoint.getSignature();
            Class returnType = signature.getReturnType();
            data=ObjectMapperUtil.toObject(result,returnType);
            System.out.println("从缓存中取值");
        }
        return data;
    }
}
