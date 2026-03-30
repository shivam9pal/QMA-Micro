package com.example.quantitymeasurementapp.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quantitymeasurementapp.dto.request.LoginRequest;
import com.example.quantitymeasurementapp.dto.response.LoginResponse;
import com.example.quantitymeasurementapp.dto.response.SignupResponse;
import com.example.quantitymeasurementapp.entity.User;
import com.example.quantitymeasurementapp.exception.AuthenticationException;
import com.example.quantitymeasurementapp.repository.UserRepository;
import com.example.quantitymeasurementapp.security.AuthUtil;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
            AuthUtil authUtil,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate user and return JWT token
     *
     * @param loginRequest containing username and password
     * @return LoginResponse with JWT token and user ID
     * @throws AuthenticationException if authentication fails
     * @throws UsernameNotFoundException if user doesn't exist
     * @throws BadCredentialsException if password is incorrect
     */
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // Validate input
            if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
                throw new AuthenticationException("Username cannot be empty");
            }
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                throw new AuthenticationException("Password cannot be empty");
            }

            // Authenticate user with AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();

            String jwtToken = authUtil.genrateAccessToken(user);
            return new LoginResponse(jwtToken, user.getId());

        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (UsernameNotFoundException | AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AuthenticationException("Authentication failed: " + ex.getMessage());
        }
    }

    /**
     * Register a new user
     *
     * @param signupRequest containing username and password
     * @return SignupResponse with user ID and username
     * @throws IllegalArgumentException if user already exists
     * @throws AuthenticationException if signup fails
     */
    public SignupResponse signup(LoginRequest signupRequest) {
        try {
            // Validate input
            if (signupRequest.getUsername() == null || signupRequest.getUsername().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            if (signupRequest.getPassword() == null || signupRequest.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            if (signupRequest.getPassword().length() < 4) {
                throw new IllegalArgumentException("Password must be at least 4 characters long");
            }

            // Check if user already exists
            User existingUser = userRepository.findByUsername(signupRequest.getUsername()).orElse(null);
            if (existingUser != null) {
                throw new IllegalArgumentException("User already exists with username: " + signupRequest.getUsername());
            }

            // Encode password before saving
            String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
            User user = userRepository.save(new User(signupRequest.getUsername(), encodedPassword));

            return new SignupResponse(user.getId(), user.getUsername());

        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AuthenticationException("Signup failed: " + ex.getMessage());
        }
    }

}
