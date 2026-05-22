package com.fooddelivery.fooddeliverybackend.config;

import com.fooddelivery.fooddeliverybackend.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Stateless Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization Rules
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/auth/**",
                                "/"
                        ).permitAll()

                        // PROFILE APIs
                        .requestMatchers(
                                "/profile"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "ADMIN",
                                "RESTAURANT_OWNER"
                        )

                        // ADMIN APIs
                        .requestMatchers(
                                "/admin/**"
                        ).hasRole("ADMIN")

                        // RESTAURANT APIs
                        .requestMatchers(
                                "/restaurant/**"
                        ).hasAnyRole(
                                "RESTAURANT_OWNER",
                                "ADMIN"
                        )

                        // FOOD APIs
                        .requestMatchers(
                                "/food/**"
                        ).hasAnyRole(
                                "RESTAURANT_OWNER",
                                "ADMIN"
                        )

                                // CART APIs
                                .requestMatchers(
                                        "/cart/**"
                                ).hasAnyRole(
                                        "CUSTOMER",
                                        "ADMIN"
                                )

// ORDER APIs
                                .requestMatchers(
                                        "/order/**"
                                ).hasAnyRole(
                                        "CUSTOMER",
                                        "ADMIN"
                                )

// PAYMENT APIs
                                .requestMatchers(
                                        "/payment/**"
                                ).hasAnyRole(
                                        "CUSTOMER",
                                        "ADMIN"
                                )
                        // All other APIs need authentication
                        .anyRequest().authenticated()
                )

                // JWT Filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}