package com.example.ContactManagementApi.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfiguration class is responsible for configuring the security
 * aspects of the application. It sets up HTTP security, authentication provider
 * and session management.
 */
@Configuration
@EnableWebSecurity // Enables Spring Security's web security support and provides the Spring MVC
					// integration.
@EnableMethodSecurity(prePostEnabled = true) // Enables method-level security with pre/post annotations.
public class SecurityConfiguration {

	// Injects Provider for authentication logic.
	@Autowired
	private AuthenticationProvider authenticationProvider;

	// Injects Custom filter for handling JWT authentication.
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * Configures the security filter chain for the application.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(cors -> cors.disable()) // Disable CORS protection.
				.csrf(csrf -> csrf.disable()) // Disable CSRF protection.
				.authorizeHttpRequests(authz -> authz.requestMatchers("/api/auth/**").permitAll() // Permit all requests
																									// to authentication
																									// endpoints.
						// .requestMatchers(HttpMethod.GET,"/users/all").hasRole("ADMIN")
						.anyRequest().authenticated() // All other requests require authentication.
				).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use
																												// stateless
																												// session
																												// management.
				).authenticationProvider(authenticationProvider) // Set the authentication provider.
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter.
				.build(); // Build and return the security filter chain.
	}

}
