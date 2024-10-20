package com.example.ContactManagementApi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ContactManagementApi.Entity.Role;
import com.example.ContactManagementApi.Entity.User;
import com.example.ContactManagementApi.Enum.RoleEnum;
import com.example.ContactManagementApi.Repository.RoleRepository;
import com.example.ContactManagementApi.Repository.UserRepository;
import com.example.ContactManagementApi.request.LoginRequest;
import com.example.ContactManagementApi.request.SignupRequest;
import jakarta.persistence.EntityNotFoundException;

/**
 * The UserService class provides various services related to user management,
 * including user registration, authentication, role assignment and CRUD
 * operations on users.
 * 
 * This class is annotated with @Service, meaning it is a Spring-managed bean
 * and is automatically detected for dependency injection.
 * 
 * Usage: This service can be used in applications where user registration,
 * authentication, and role-based access control are needed.
 */
@Service
public class UserService {

	// Injects the UserRepository for interacting with user data in the database.
	@Autowired
	private UserRepository userRepository;

	// Injects the RoleRepository for interacting with roles in the database.
	@Autowired
	private RoleRepository roleRepository;

	// Injects PasswordEncoder to encode user passwords for security.
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Injects AuthenticationManager to handle user authentication in Spring
	// Security.
	@Autowired
	private AuthenticationManager authenticationManager;

	// Registers a new user with the role of ROLE_USER.
	public User registerUser(SignupRequest signupRequest) {
		return registerUserOrAdmin(signupRequest, RoleEnum.ROLE_USER);
	}

	// Registers a new admin with the role of ROLE_ADMIN.
	public User registerAdmin(SignupRequest signupRequest) {
		return registerUserOrAdmin(signupRequest, RoleEnum.ROLE_ADMIN);
	}

	/**
	 * A private helper method for registering either a user or an admin. It checks
	 * if the username already exists, encodes the password, assigns the appropriate
	 * role, and saves the user to the database.
	 */
	private User registerUserOrAdmin(SignupRequest signupRequest, RoleEnum roleEnum) {
		// Check if the username already exists in the system.
		if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username is already taken!");
		}

		// Create a new User and set its username and encoded password.
		User user = new User();
		user.setUsername(signupRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

		// Retrieve the role from the RoleRepository and assign it to the user.
		Role role = roleRepository.findByName(roleEnum);
		if (role == null) {
			throw new IllegalArgumentException(roleEnum.name() + " role not found");
		}
		user.getRoles().add(role); // Add the role to the user's role set.

		// Save the new user to the database.
		return userRepository.save(user);
	}

	/**
	 * Authenticates a user based on the provided credentials and checks if the user
	 * has the required role.
	 */
	public User authenticate(LoginRequest input, RoleEnum requiredRole) throws AuthenticationException {
		// Authenticate the user based on username and password.
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

		// Set the authentication in the security context.
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Retrieve the authenticated user's details.
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByUsername(input.getUsername())
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		// Verify if the user has the required role.
		if (!hasRole(userDetails.getUsername(), requiredRole)) {
			throw new IllegalArgumentException("User does not have the required role: " + requiredRole);
		}

		return user; // Return the authenticated user.
	}

	// Checks if a user has a specific role.
	public boolean hasRole(String username, RoleEnum roleEnum) {
		// Retrieve the user by username.
		User user = userRepository.findByUsername(username).orElse(null);

		// If the user exists, check if they have the specified role.
		if (user != null) {
			return user.getRoles().stream().anyMatch(role -> role.getName() == roleEnum);
		}
		return false;
	}

	// Retrieves a list of all users from the database.
	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	// Updates an existing user's information, including their username and
	// password.
	public User updateUser(Long id, User user) {
		// Retrieve the existing user by ID.
		Optional<User> existingUserOpt = userRepository.findById(id);
		if (existingUserOpt.isPresent()) {
			User existingUser = existingUserOpt.get();

			// Update the user's username and password.
			existingUser.setUsername(user.getUsername());
			existingUser.setPassword(user.getPassword());

			// Save the updated user to the database.
			return userRepository.save(existingUser);
		} else {
			throw new EntityNotFoundException("User not found");
		}
	}

	// Deletes a user from the database by their ID.
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

}
