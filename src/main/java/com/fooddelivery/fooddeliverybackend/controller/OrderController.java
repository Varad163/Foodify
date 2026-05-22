package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.entity.Order;

import com.fooddelivery.fooddeliverybackend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place Order
    @PostMapping("/place")
    public String placeOrder(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return orderService.placeOrder(email);
    }

    // Get My Orders
    @GetMapping("/my")
    public List<Order> getMyOrders(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return orderService.getMyOrders(email);
    }
}