package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class GxdSleuthZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxdSleuthZipkinApplication.class, args);
	}
}
