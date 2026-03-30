package com.example.quantitymeasurementapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.quantitymeasurementapp.dto.request.LoginRequest;
import com.example.quantitymeasurementapp.dto.response.LoginResponse;
import com.example.quantitymeasurementapp.dto.response.SignupResponse;
import com.example.quantitymeasurementapp.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Authenticate user and generate JWT token
     *
     * @param loginRequest containing username and password
     * @return JWT token and user ID on success
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new IllegalArgumentException("Login request cannot be null");
        }

        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Register a new user
     *
     * @param loginRequest containing username and password
     * @return User ID and username on success
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new IllegalArgumentException("Signup request cannot be null");
        }

        SignupResponse response = authService.signup(loginRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("")
    public String get(){
        return "yes u can access";
    }
}
