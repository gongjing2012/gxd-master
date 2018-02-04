package com.gxd;

import com.gxd.common.datasource.DynamicDataSourceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Import({ DynamicDataSourceRegister.class }) //集成druid多数据源
@EnableTransactionManagement //开启事务
@ServletComponentScan //开启自动注解
@EnableDiscoveryClient //向Eureka注册
@SpringBootApplication //声明启动项
@RestController
public class GxdProviderApplication {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(GxdProviderApplication.class, args);
	}
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@RequestMapping("/hi")
	public String callHome(){
		logger.info("calling trace provider1  ");
		return restTemplate.getForObject("http://localhost:8082/miya", String.class);
	}
	@RequestMapping("/info")
	public String info(){
		logger.info("calling trace service-hi ");
		return "i'm service-hi";
	}

	@Bean // 此处可以使用配置说明
	public Sampler defaultSampler() {
		return new AlwaysSampler();
	}

}
