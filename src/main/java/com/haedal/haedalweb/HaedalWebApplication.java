package com.haedal.haedalweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class HaedalWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(HaedalWebApplication.class, args);
	}
}
