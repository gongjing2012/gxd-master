package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync   //（异步回调）让@Async注解能够生效,不能加在静态方法上
@EnableScheduling
@ServletComponentScan
@SpringBootApplication
public class GxdComponentsRocketmqApplication {

	public static void main(String[] args) {

		//ApplicationContext context = SpringApplication.run(GxdComponentsRocketmqApplication.class,args);
		//DefaultMQProducer defaultMQProducer = context.getBean(DefaultMQProducer.class);
		//Message msg = new Message("TEST",// topic
		//		"TEST",// tag
		//		"KKK",//key用于标识业务的唯一性
		//		("Hello RocketMQ !!!!!!!!!!" ).getBytes()// body 二进制字节数组
		//);
		//SendResult result = defaultMQProducer.send(msg);
		//System.out.println(result);
		//DefaultMQPushConsumer consumer = context.getBean(DefaultMQPushConsumer.class);
		SpringApplication.run(GxdComponentsRocketmqApplication.class,args);
	}
}
