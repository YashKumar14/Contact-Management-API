package com.example.ContactManagementApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Contact Management API application.
 * 
 * This class serves as the main configuration and bootstrap for the Spring Boot
 * application. It enables auto-configuration and component scanning within the
 * specified package.
 */
@SpringBootApplication
public class ContactManagementApiApplication {

	/**
	 * The main method which serves as the entry point for the Spring Boot
	 * application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ContactManagementApiApplication.class, args);
	}

}
