package com.example.stamp_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class StampSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StampSpringbootApplication.class, args);
	}

}
