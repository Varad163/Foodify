package com.fooddelivery.fooddeliverybackend.config;

import com.fooddelivery.fooddeliverybackend.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:3000")
        );

        configuration.setAllowedMethods(
                List.of("*")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // ENABLE CORS
                .cors(Customizer.withDefaults())

                // DISABLE CSRF
                .csrf(csrf -> csrf.disable())

                // STATELESS SESSION
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // AUTHORIZATION RULES
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC APIs
                        .requestMatchers(
                                "/api/auth/**",
                                "/auth/**",
                                "/",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/test.html",
                                "/ws/**"
                        ).permitAll()

                        // PROFILE APIs
                        .requestMatchers(
                                "/profile"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "ADMIN",
                                "RESTAURANT_OWNER",
                                "DELIVERY_PARTNER"
                        )

                        // ADMIN APIs
                        .requestMatchers(
                                "/admin/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/restaurant/orders"
                        )
                        .hasAnyRole(
                                "RESTAURANT_OWNER",
                                "ADMIN"
                        )
                        
                        .requestMatchers(
                                "/restaurant/confirm/**"
                        )
                        .hasAnyRole(
                                "RESTAURANT_OWNER",
                                "ADMIN"
                        )
                        .requestMatchers(
                                "/restaurant/**"
                        )
                        .hasAnyRole(
                                "RESTAURANT_OWNER",
                                "ADMIN"
                        )
                        // FOOD APIs
                        .requestMatchers(
                                "/food/all",
                                "/food/restaurant/**"
                        ).permitAll()

                        .requestMatchers(
                                "/food/add"
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
                        .requestMatchers("/order/my-orders")
                        .hasAnyRole(
                                "CUSTOMER",
                                "ADMIN"
                        )

                        .requestMatchers("/order/**")
                        .hasAnyRole(
                                "CUSTOMER",
                                "RESTAURANT_OWNER",
                                "ADMIN"
                        )

                        // PAYMENT APIs
                        .requestMatchers(
                                "/payment/**"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "ADMIN"
                        )

                        // DELIVERY APIs
                        .requestMatchers(
                                "/delivery/**"
                        ).hasAnyRole(
                                "DELIVERY_PARTNER",
                                "ADMIN"
                        )

                        // ADDRESS APIs
                        .requestMatchers(
                                "/address/**"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "ADMIN"
                        )

                        // ALL OTHER APIs
                        .anyRequest().authenticated()
                )

                // JWT FILTER
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}