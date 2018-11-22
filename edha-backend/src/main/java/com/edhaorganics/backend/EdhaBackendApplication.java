package com.edhaorganics.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EdhaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdhaBackendApplication.class, args);
	}
	
}

