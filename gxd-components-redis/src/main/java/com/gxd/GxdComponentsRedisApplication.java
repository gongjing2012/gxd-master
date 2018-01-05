package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableTransactionManagement //开启事务
@ServletComponentScan //开启自动注解
@EnableDiscoveryClient //向Eureka注册
@SpringBootApplication //声明启动项
public class GxdComponentsRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxdComponentsRedisApplication.class, args);
	}
}
