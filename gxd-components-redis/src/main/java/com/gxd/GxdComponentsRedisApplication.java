package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //开启自动注解
@SpringBootApplication //声明启动项
public class GxdComponentsRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxdComponentsRedisApplication.class, args);
	}
}
