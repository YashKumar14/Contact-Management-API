package com.example.ContactManagementApi.response;

/**
 * Represents a JSON Web Token (JWT) response.
 * 
 * This class is used to encapsulate the information returned to the client upon
 * successful authentication. It contains the JWT token and the duration for
 * which the token is valid.
 */
public class JwtResponse {

	/**
	 * The JWT token generated upon successful authentication.
	 */
	private String token;

	/**
	 * The duration in milliseconds for which the token is valid.
	 */
	private long expiresIn;

	/**
	 * Constructs a new {@link JwtResponse} with the specified token and expiration
	 * time.
	 */
	public JwtResponse(String token, long expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

}
