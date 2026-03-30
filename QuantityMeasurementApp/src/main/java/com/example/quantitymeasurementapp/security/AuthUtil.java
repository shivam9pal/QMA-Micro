package com.example.quantitymeasurementapp.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.quantitymeasurementapp.entity.User;
import com.example.quantitymeasurementapp.exception.InvalidTokenException;
import com.example.quantitymeasurementapp.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtil {

    private final String jwtSecretKey = "MySuperStrongSecretKeyThatIsAtLeast32CharsLong!!";

    //We are making our key to secret Key conversion
    private SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    //Here we are building the toke for user to return 
    public String genrateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Validate JWT token Checks signature and expiration Throws
     * TokenExpiredException if token is expired Throws InvalidTokenException if
     * token is invalid
     */
    public void validateToken(String token) {
        try {
            System.out.println(token+"token");
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException("Token has expired");
        } catch (JwtException ex) {
            ex.printStackTrace();
            throw new InvalidTokenException("Invalid or malformed token");
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("Token is empty or null");
        }
    }

    /**
     * Check if token is valid (without throwing exceptions) Used for filter
     * chain processing
     */
    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Extract username from JWT token
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (JwtException ex) {
            return null;
        }
    }

    /**
     * Extract userId from JWT token
     */
    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return (String) claims.get("userId");
        } catch (JwtException ex) {
            return null;
        }
    }

    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getExpiration().before(new Date());
        } catch (JwtException ex) {
            return true;
        }
    }
}
