package com.example.ContactManagementApi.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a login request containing the user's credentials.
 * 
 * This class is used to encapsulate the login information that a user submits
 * when attempting to authenticate in the application. It includes fields for
 * the username and password, both of which are required.
 * 
 */
public class LoginRequest {

	/**
	 * The username of the user attempting to log in. This field must not be blank.
	 */
	@NotBlank
	private String username;

	/**
	 * The password of the user attempting to log in. This field must not be blank.
	 */
	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
