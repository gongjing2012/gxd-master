package com.gxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GxdTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxdTurbineApplication.class, args);
	}
}
