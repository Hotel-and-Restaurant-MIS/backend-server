package com.luxury.virtualwaiter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VirtualwaiterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualwaiterServiceApplication.class, args);
	}

}
