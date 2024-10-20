package com.example.ContactManagementApi.Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for managing JWT (JSON Web Tokens) operations.
 * 
 * This class provides methods for generating JWTs, validating them, and
 * extracting information such as expiration date and username from the token.
 */
@Component
public class JwtUtils {
	// The secret key used for signing JWT tokens, injected from application
	// properties.
	@Value("${security.jwt.secret-key}")
	private String secretKey;

	// The duration (in milliseconds) for which the JWT token is valid, injected
	// from application properties.
	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	/**
	 * Generates a JWT token based on user details.
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList())); // Add user
																											// roles to
																											// claims
		return createToken(claims, userDetails); // Create and return the token
	}

	/**
	 * Creates a JWT token with the specified claims and user details.
	 */
	private String createToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()) // Set the subject (username)
				.setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued date
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Set the expiration date
				.signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign the token with the secret key
				.compact(); // Build and return the token
	}

	/**
	 * Retrieves the signing key for signing and verifying JWTs.
	 */
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decode the base64 secret key
		return Keys.hmacShaKeyFor(keyBytes); // Generate a signing key
	}

	/**
	 * Validates a JWT token by checking the username and expiration status.
	 */
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromtoken(token); // Extract username from the token
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Check validity
	}

	/**
	 * Checks if the given token has expired.
	 */
	private boolean isTokenExpired(String token) {
		return getExpirationFromToken(token).before(new Date()); // Compare expiration date with current date
	}

	/**
	 * Retrieves the expiration date from the given token.
	 */
	public Date getExpirationFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()) // Set the signing key for parsing
				.build().parseClaimsJws(token) // Parse the JWT and retrieve claims
				.getBody().getExpiration(); // Return the expiration date
	}

	/**
	 * Extracts the username from the given token.
	 */
	public String getUsernameFromtoken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()) // Set the signing key for parsing
				.build().parseClaimsJws(token) // Parse the JWT and retrieve claims
				.getBody().getSubject(); // Return the subject (username)

	}

	/**
	 * Retrieves the expiration time for the JWT.
	 */
	public Long getExpirationTime() {
		return jwtExpiration;
	}

}
