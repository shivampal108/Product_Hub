package com.product.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProductHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductHubApplication.class, args);
	}

}
