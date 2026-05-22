package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.LoginRequest;
import com.fooddelivery.fooddeliverybackend.dto.SignupRequest;
import com.fooddelivery.fooddeliverybackend.entity.Role;
import com.fooddelivery.fooddeliverybackend.entity.User;
import com.fooddelivery.fooddeliverybackend.repository.UserRepository;
import com.fooddelivery.fooddeliverybackend.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // REGISTER USER
    public String register(SignupRequest request) {

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        // Create new user
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Default role
        user.setRole(Role.CUSTOMER);

        // Save user
        userRepository.save(user);

        return "User registered successfully";
    }

    // LOGIN USER
    public String login(LoginRequest request) {

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Fetch user from database
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // Generate JWT token
        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
    }
}