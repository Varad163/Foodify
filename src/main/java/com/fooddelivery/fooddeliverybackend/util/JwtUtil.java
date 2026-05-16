package com.fooddelivery.fooddeliverybackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key
    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12";

    // Token validity = 1 day
    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 24;

    // Generate JWT token
    public String generateToken(String email) {

        Key key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {

        Claims claims = extractAllClaims(token);

        return claims.getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {

        try {

            extractAllClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // Extract claims
    private Claims extractAllClaims(String token) {

        Key key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}