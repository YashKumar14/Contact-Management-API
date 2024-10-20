package com.example.ContactManagementApi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ContactManagementApi.Entity.Role;
import com.example.ContactManagementApi.Enum.RoleEnum;
import com.example.ContactManagementApi.Repository.RoleRepository;

/**
 * The RolesSeeding class is responsible for ensuring that predefined roles
 * (ROLE_ADMIN and ROLE_USER) exist in the database when the application starts.
 * 
 * It implements CommandLineRunner, meaning it will run after the application
 * context is loaded and right before the Spring Boot application starts.
 * 
 * This class is annotated with @Component}, allowing it to be automatically
 * detected and instantiated by Spring as a bean.
 * 
 * Usage: This class should be included in an application where role-based
 * access control is required and predefined roles need to be seeded upon
 * startup.
 */
@Component
public class RolesSeeding implements CommandLineRunner {

	// Injecting the RoleRepository to perform database operations related to roles
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * This method is called when the application starts. It triggers the role
	 * seeding process to ensure necessary roles are present in the database.
	 */
	@Override
	public void run(String... args) throws Exception {
		seedRoles();
	}

	/**
	 * Seeds the roles into the database if they do not already exist. Specifically,
	 * it checks for the presence of ROLE_ADMIN and ROLE_USER.
	 */
	private void seedRoles() {
		// Check if the ROLE_ADMIN exists in the repository
		if (roleRepository.findByName(RoleEnum.ROLE_ADMIN) == null) {
			Role adminRole = new Role(); // Create a new Role object
			adminRole.setName(RoleEnum.ROLE_ADMIN); // Set the role name to ROLE_ADMIN
			roleRepository.save(adminRole); // Save the admin role to the repository
		}

		// Check if the ROLE_USER exists in the repository
		if (roleRepository.findByName(RoleEnum.ROLE_USER) == null) {
			Role userRole = new Role(); // Create a new Role object
			userRole.setName(RoleEnum.ROLE_USER); // Set the role name to ROLE_ADMIN
			roleRepository.save(userRole); // Save the admin role to the repository
		}

	}

}
