package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class HypermediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HypermediaApplication.class, args);
	}

}
