package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//向Eureka注册
@EnableAutoConfiguration
@EnableDiscoveryClient
public class GxdComponentsEsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxdComponentsEsApplication.class, args);
	}
}
