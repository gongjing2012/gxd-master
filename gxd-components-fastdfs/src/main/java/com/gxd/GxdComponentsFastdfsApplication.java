package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan
@SpringBootApplication
public class GxdComponentsFastdfsApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(GxdComponentsFastdfsApplication.class, args);
	}
	/**
	 *  favorPathExtension表示支持后缀匹配，
	 *  属性ignoreAcceptHeader默认为fasle，表示accept-header匹配，
	 *  defaultContentType开启默认匹配。
	 * @param configurer
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}
}
