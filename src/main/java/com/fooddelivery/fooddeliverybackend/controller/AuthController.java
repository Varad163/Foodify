package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.SignupRequest;
import com.fooddelivery.fooddeliverybackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String signup(
            @RequestBody SignupRequest request
    ) {

        return authService.signup(request);
    }
}