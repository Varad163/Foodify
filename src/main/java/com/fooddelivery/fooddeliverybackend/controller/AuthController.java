package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.ApiResponse;
import com.fooddelivery.fooddeliverybackend.dto.AuthResponse;
import com.fooddelivery.fooddeliverybackend.dto.LoginRequest;
import com.fooddelivery.fooddeliverybackend.dto.SignupRequest;

import com.fooddelivery.fooddeliverybackend.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // REGISTER API
    @PostMapping("/register")
    public ApiResponse<String> register(
            @Valid @RequestBody SignupRequest request
    ) {

        String response = authService.register(request);

        return new ApiResponse<>(
                true,
                response,
                null
        );
    }

    // LOGIN API
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        AuthResponse response =
                authService.login(request);

        return new ApiResponse<>(
                true,
                "Login successful",
                response
        );
    }
}