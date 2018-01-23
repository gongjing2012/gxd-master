package com.gxd;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author gongjing
 */
@SpringBootApplication
@EnableDiscoveryClient //向Eureka注册
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableApolloConfig
@ComponentScan
public class GxdSsoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GxdSsoApplication.class, args);
	}
}
