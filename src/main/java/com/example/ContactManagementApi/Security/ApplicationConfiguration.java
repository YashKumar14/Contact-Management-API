package com.example.ContactManagementApi.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.ContactManagementApi.Repository.UserRepository;

/**
 * Configuration class for setting up security-related beans.
 * 
 * This class configures the user details service, password encoder,
 * authentication manager and authentication provider to be used in the
 * application for user authentication and authorization.
 */
@Configuration
public class ApplicationConfiguration {

	// Inject Repository to access user data
	@Autowired
	private UserRepository userRepository;

	/**
	 * Configures a UserDetailsService bean that retrieves user details from the
	 * database by username.
	 */
	@Bean
	UserDetailsService userDetailsService() {
		// Retrieve user details by username; throws exception if not found
		return username -> userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	/**
	 * Configures a BCryptPasswordEncoder bean for password encoding.
	 */
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		// Create and return a password encoder using BCrypt algorithm
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures an AuthenticationManager bean.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		// Retrieve and return the authentication manager from the provided
		// configuration
		return config.getAuthenticationManager();
	}

	/**
	 * Configures an AuthenticationProvider bean that uses a
	 * DaoAuthenticationProvider to authenticate users based on their username and
	 * password.
	 */
	@Bean
	AuthenticationProvider authenticationProvider() {
		// Create a DaoAuthenticationProvider to authenticate users with database-backed
		// credentials
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		// Set the user details service and password encoder for the provider
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider; // Return the configured authentication provider
	}

}
