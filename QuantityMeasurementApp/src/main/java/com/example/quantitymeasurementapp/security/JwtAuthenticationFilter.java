package com.example.quantitymeasurementapp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.quantitymeasurementapp.exception.InvalidTokenException;
import com.example.quantitymeasurementapp.exception.TokenExpiredException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Extract JWT token from Authorization header
            String token = extractTokenFromRequest(request);

            // If token exists, validate and set authentication
            if (token != null) {
                try {
                    // Validate token (throws specific exceptions if invalid/expired)
                    authUtil.validateToken(token);

                    // Get username from token
                    String username = authUtil.getUsernameFromToken(token);

                    if (username != null) {
                        // Load user details
                        try {
                            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                            // Create authentication token
                            UsernamePasswordAuthenticationToken authenticationToken
                                    = new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());

                            // Set authentication details
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            // Set authentication in security context
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        } catch (UsernameNotFoundException ex) {
                            logger.warn("User not found for token: " + username);
                            // Continue without authentication - endpoint will return 401 if required
                        }
                    }
                } catch (TokenExpiredException ex) {
                    logger.warn("Token expired for request");
                    // Continue to endpoint - endpoint will handle authorization
                } catch (InvalidTokenException ex) {
                    logger.warn("Invalid token for request");
                    // Continue to endpoint - endpoint will handle authorization
                }
            }
        } catch (Exception ex) {
            logger.error("Could not process authentication token", ex);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from Authorization header Expected format: Bearer
     * <token>
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
}
