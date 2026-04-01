package com.apigateway.config;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    public static final List<String> OPEN_ENDPOINTS = List.of(
            "/auth/signup",     // ✅ Matches your actual gateway route /auth/**
            "/auth/login",        // ✅ Matches your actual gateway route /auth/**
            "/eureka",
            "/auth/signup",
            "/auth/login",
            "/eureka",
            "/swagger-ui",
            "/swagger-ui.html",
            "/v3/api-docs"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> OPEN_ENDPOINTS.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
