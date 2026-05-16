package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.LoginRequest;
import com.fooddelivery.fooddeliverybackend.dto.SignupRequest;
import com.fooddelivery.fooddeliverybackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Signup API
    @PostMapping("/signup")
    public String signup(
            @RequestBody SignupRequest request
    ) {

        return authService.signup(request);
    }

    // Login API
    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request
    ) {

        return authService.login(request);
    }
}