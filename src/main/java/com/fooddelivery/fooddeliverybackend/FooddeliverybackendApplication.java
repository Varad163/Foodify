package com.fooddelivery.fooddeliverybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FooddeliverybackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				FooddeliverybackendApplication.class,
				args
		);
	}
}