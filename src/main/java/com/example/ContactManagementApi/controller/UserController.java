package com.example.ContactManagementApi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ContactManagementApi.Entity.User;
import com.example.ContactManagementApi.Service.UserService;

/**
 * UserController handles user-related operations such as fetching,updating and
 * deleting users in the system.
 * 
 * This controller provides endpoints for both authenticated users and
 * administrators. Users can retrieve their own information,while administrators
 * can manage all users.
 * 
 */
@RequestMapping("/users")
@RestController
public class UserController {

	// Injects the UserService to handle business logic related to user operations.
	@Autowired
	private UserService userService;

	
	/**
	 * Fetches the currently authenticated user. This method retrieves the user that
	 * is currently authenticated from the security context.
	 */
	@GetMapping("/current")
	public ResponseEntity<User> authenticatedUser() {
		// Retrieves the authentication object from the security context.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Retrieves the authenticated user (User principal) from the authentication
		// object.
		User currentUser = (User) authentication.getPrincipal();
		return ResponseEntity.ok(currentUser); // Returns a 200 OK response with the current user details.
	}

	
	/**
	 * Retrieves all users in the system. Only users with the 'ADMIN' role can
	 * access this endpoint.
	 */
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')") // Authorizes only 'ADMIN' role to access this endpoint.
	public ResponseEntity<List<User>> allUsers() {
		// Calls the service layer to fetch all users
		List<User> users = userService.allUsers();
		return ResponseEntity.ok(users); // Returns a 200 OK response with the list of all users.
	}

	
	/**
	 * Updates an existing user by their ID. Only users with the 'ADMIN' role can
	 * access this endpoint.
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Authorizes only 'ADMIN' role to access this endpoint.
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		// Calls the service layer to update the user based on the provided ID and user
		// details.
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(updatedUser); // Returns a 200 OK response with the updated user details.
	}

	
	/**
	 * Deletes a user by their ID. Only users with the 'ADMIN' role can access this
	 * endpoint.
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')") // Authorizes only 'ADMIN' role to access this endpoint.
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		// Calls the service layer to delete the user by the provided ID.
		userService.deleteUser(id);
		return ResponseEntity.noContent().build(); // Returns a 204 No Content response indicating successful deletion.
	}

}
