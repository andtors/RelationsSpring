package com.example.relational_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class RelationalTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelationalTestApplication.class, args);
	}

}
