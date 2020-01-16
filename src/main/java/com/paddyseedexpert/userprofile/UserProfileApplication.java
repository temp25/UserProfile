package com.paddyseedexpert.userprofile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserProfileApplication implements CommandLineRunner{
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserProfileApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(UserProfileApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("User profile Spring Boot application started...");
	}
	
}