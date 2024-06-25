package com.tax.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableFeignClients
public class TaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxApplication.class, args);
	}

}
