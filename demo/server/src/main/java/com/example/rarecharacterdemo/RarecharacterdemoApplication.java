package com.example.rarecharacterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.alipay.rarecharacter","com.example.rarecharacterdemo"})
@SpringBootApplication
public class RarecharacterdemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(RarecharacterdemoApplication.class, args);
	}
}
