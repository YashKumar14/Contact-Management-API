package com.example.ContactManagementApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ContactManagementApi.Entity.User;
import com.example.ContactManagementApi.Enum.RoleEnum;
import com.example.ContactManagementApi.Security.JwtUtils;
import com.example.ContactManagementApi.Service.UserService;
import com.example.ContactManagementApi.request.LoginRequest;
import com.example.ContactManagementApi.request.SignupRequest;
import com.example.ContactManagementApi.response.JwtResponse;
import jakarta.validation.Valid;

/**
 * The AuthController class handles user and admin authentication and
 * registration requests. It provides endpoints for signing up users and admins
 * and authenticating them using JWT tokens.
 * 
 * This controller interacts with the UserService to handle business logic
 * related to user management, and JwtUtils to generate JWT tokens for
 * authenticated users and admins.
 * 
 * Base path for all endpoints in this controller is /api/auth.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthContoller {

	// Injects the UserService to handle user registration and authentication.
	@Autowired
	private UserService userService;

	// Injects JwtUtils to generate and manage JWT tokens.
	@Autowired
	private JwtUtils jwtUtils;

	
	// Registers a new user with the role of ROLE_USER.
	@PostMapping("/signup/user")
	public ResponseEntity<?> signupUser(@RequestBody @Valid SignupRequest signupRequest) {
		try {
			// Calls UserService to register the user with ROLE_USER.
			User createdUser = userService.registerUser(signupRequest);
			return ResponseEntity.ok(createdUser);
		} catch (IllegalArgumentException e) {
			// Returns a bad request if there's an error (e.g., username already taken).
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}

	
	// Registers a new admin with the role of ROLE_ADMIN.
	@PostMapping("/signup/admin")
	public ResponseEntity<?> signupAdmin(@RequestBody @Valid SignupRequest signupRequest) {
		try {
			// Calls UserService to register the user with ROLE_ADMIN.
			User createdAdmin = userService.registerAdmin(signupRequest);
			return ResponseEntity.ok(createdAdmin);
		} catch (IllegalArgumentException e) {
			// Returns a bad request if there's an error (e.g., username already taken).
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}

	
	// Authenticates a user with the ROLE_USER and generates a JWT token.
	@PostMapping("/login/user")
	public ResponseEntity<JwtResponse> loginUser(@RequestBody @Valid LoginRequest loginUserDto) {
		// Calls UserService to authenticate the user with ROLE_USER.
		User authenticatedUser = userService.authenticate(loginUserDto, RoleEnum.ROLE_USER);

		// Generates a JWT token for the authenticated user.
		String jwtToken = jwtUtils.generateToken(authenticatedUser);

		// Creates a JWT response containing the token and expiration time.
		JwtResponse loginResponse = new JwtResponse(jwtToken, jwtUtils.getExpirationTime());

		return ResponseEntity.ok(loginResponse);
	}

	
	// Authenticates an admin with the ROLE_ADMIN and generates a JWT token.
	@PostMapping("/login/admin")
	public ResponseEntity<JwtResponse> loginAdmin(@RequestBody @Valid LoginRequest loginUserDto) {
		// Calls UserService to authenticate the admin with ROLE_ADMIN.
		User authenticatedAdmin = userService.authenticate(loginUserDto, RoleEnum.ROLE_ADMIN);

		// Generates a JWT token for the authenticated admin.
		String jwtToken = jwtUtils.generateToken(authenticatedAdmin);

		// Creates a JWT response containing the token and expiration time.
		JwtResponse loginResponse = new JwtResponse(jwtToken, jwtUtils.getExpirationTime());

		return ResponseEntity.ok(loginResponse);
	}

}
