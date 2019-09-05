package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2019/7/19.
 */
@SpringBootApplication
@MapperScan("com.jt.mapper")
public class SpringBootRunCart {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRunCart.class, args);
    }
}
