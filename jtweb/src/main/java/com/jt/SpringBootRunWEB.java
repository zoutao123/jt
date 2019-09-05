package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
//@SpringBootApplication
public class SpringBootRunWEB {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRunWEB.class,args);
	}
}
