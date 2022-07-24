package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcApplication.class, args);
		System.out.println("Auto Seller Start");
	}
}
