package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.LoginRequest;
import com.fooddelivery.fooddeliverybackend.dto.SignupRequest;
import com.fooddelivery.fooddeliverybackend.entity.Role;
import com.fooddelivery.fooddeliverybackend.entity.User;
import com.fooddelivery.fooddeliverybackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Signup Logic
    public String signup(SignupRequest request) {

        // Check if email already exists
        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()) {

            return "Email Already Registered";
        }

        // Create new user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.CUSTOMER)
                .build();

        // Save user in database
        userRepository.save(user);

        return "User Registered Successfully";
    }

    // Login Logic
    public String login(LoginRequest request) {

        Optional<User> optionalUser =
                userRepository.findByEmail(request.getEmail());

        // Check user exists
        if(optionalUser.isEmpty()) {

            return "User Not Found";
        }

        User user = optionalUser.get();

        // Compare password
        boolean isPasswordMatch =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if(!isPasswordMatch) {

            return "Invalid Password";
        }

        return "Login Successful";
    }
}