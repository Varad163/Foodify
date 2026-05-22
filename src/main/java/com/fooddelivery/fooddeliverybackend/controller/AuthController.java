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

    // REGISTER API
    @PostMapping("/register")
    public String register(
            @RequestBody SignupRequest request
    ) {

        return authService.register(request);
    }

    // LOGIN API
    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request
    ) {

        return authService.login(request);
    }
}