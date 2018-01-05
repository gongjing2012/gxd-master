package com.gxd;

import com.gxd.common.datasource.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Import({ DynamicDataSourceRegister.class }) //集成druid多数据源
@EnableTransactionManagement //开启事务
@ServletComponentScan //开启自动注解
@EnableDiscoveryClient //向Eureka注册
@SpringBootApplication //声明启动项
public class GxdProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxdProviderApplication.class, args);
	}
}
