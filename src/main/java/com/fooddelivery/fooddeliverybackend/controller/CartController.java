package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.CartRequest;
import com.fooddelivery.fooddeliverybackend.dto.CartResponse;

import com.fooddelivery.fooddeliverybackend.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")

public class CartController {

    @Autowired
    private CartService cartService;

    // Add To Cart
    @PostMapping("/add")
    public String addToCart(
            @RequestBody CartRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return cartService.addToCart(
                request,
                email
        );
    }

    // Get My Cart
    @GetMapping("/my")
    public List<CartResponse> getMyCart(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return cartService.getMyCart(email);
    }
}