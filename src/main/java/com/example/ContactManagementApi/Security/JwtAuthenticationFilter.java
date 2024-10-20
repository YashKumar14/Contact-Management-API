package com.example.ContactManagementApi.Security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter that handles JWT authentication for incoming requests.
 * 
 * This filter processes each request and checks for the presence of a JWT token
 * in the Authorization header. If a valid token is found, it authenticates the
 * user.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// Handles exceptions during request processing
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;

	// Injects Utility class for JWT operations
	@Autowired
	private JwtUtils jwtUtils;

	// Injects Service to load user details
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Processes the incoming request and checks for a JWT token in the
	 * Authorization header.
	 * 
	 * If a valid token is found, it authenticates the user and sets the
	 * authentication in the SecurityContext. If any exceptions occur, they are
	 * resolved using the HandlerExceptionResolver.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization"); // Get the Authorization header

		// Check if the Authorization header is present and starts with "Bearer "
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response); // Proceed without authentication
			return;
		}

		try {
			// Extract the JWT from the Authorization header
			final String jwt = authHeader.substring(7);
			final String username = jwtUtils.getUsernameFromtoken(jwt); // Get the username from the token

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Retrieve current
																									// authentication

			// If the username is valid and no authentication exists, authenticate the user
			if (username != null && authentication == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // Load user details

				// Validate the JWT token
				if (jwtUtils.isTokenValid(jwt, userDetails)) {
					// Create authentication token and set it in the SecurityContext
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Set additional
																										// details
					SecurityContextHolder.getContext().setAuthentication(authToken); // Set authentication in context
				}
			}
			filterChain.doFilter(request, response); // Continue with the filter chain
		} catch (Exception e) {
			// Resolve any exceptions that occur during processing
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
	}

}
