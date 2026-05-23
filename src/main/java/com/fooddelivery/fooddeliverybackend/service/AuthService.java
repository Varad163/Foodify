package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.AuthResponse;
import com.fooddelivery.fooddeliverybackend.dto.LoginRequest;
import com.fooddelivery.fooddeliverybackend.dto.SignupRequest;

import com.fooddelivery.fooddeliverybackend.entity.Role;
import com.fooddelivery.fooddeliverybackend.entity.User;

import com.fooddelivery.fooddeliverybackend.exception.UserAlreadyExistsException;
import com.fooddelivery.fooddeliverybackend.exception.UserNotFoundException;

import com.fooddelivery.fooddeliverybackend.repository.UserRepository;
import com.fooddelivery.fooddeliverybackend.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

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

        logger.info(
                "Register request received for email: {}",
                request.getEmail()
        );

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new UserAlreadyExistsException(
                    "Email already exists"
            );
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

        logger.info(
                "User registered successfully: {}",
                user.getEmail()
        );

        return "User registered successfully";
    }

    // LOGIN USER
    public AuthResponse login(LoginRequest request) {

        logger.info(
                "Login request for email: {}",
                request.getEmail()
        );

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

                        new UserNotFoundException(
                                "User not found"
                        )
                );

        // Generate JWT token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        logger.info(
                "JWT token generated for: {}",
                user.getEmail()
        );

        // Return professional auth response
        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}