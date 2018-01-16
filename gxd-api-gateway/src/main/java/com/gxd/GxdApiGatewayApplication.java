package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GxdApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxdApiGatewayApplication.class, args);
	}
}
