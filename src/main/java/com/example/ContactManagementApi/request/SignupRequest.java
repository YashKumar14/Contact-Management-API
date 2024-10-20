package com.example.ContactManagementApi.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a signup request containing the user's registration information.
 * 
 * This class is used to encapsulate the data required for a user to register an
 * account in the application. It includes fields for the username and password,
 * both of which are required.
 * 
 */
public class SignupRequest {

	/**
	 * The username of the user attempting to sign up. This field must not be blank.
	 */
	@NotBlank
	private String username;

	/**
	 * The password of the user attempting to sign up. This field must not be blank.
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
