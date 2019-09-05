package com.jt.aspect;

import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Aspect
@RestControllerAdvice   //针对Controller层生效
@Slf4j
public class SysResultAspect {
    /**
     * 如果程序报错,则统一返回系统异常信息
     * SysResult.fail()
     */
    @ExceptionHandler(RuntimeException.class)   //如果遇到指定的异常类型执行下列方法
    public SysResult sysResultFail(Exception e) {
        System.out.println("~~~~服务器异常:"+e.getMessage());
        log.error(e.getMessage());
        return SysResult.fail();
    }
}
