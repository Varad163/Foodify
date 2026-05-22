package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.OrderResponse;
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

    // PLACE ORDER
    @PostMapping("/place")
    public String placeOrder(
            Authentication authentication
    ) {

        return orderService.placeOrder(
                authentication.getName()
        );
    }

    // GET MY ORDERS
    @GetMapping("/my")
    public List<OrderResponse> getMyOrders(
            Authentication authentication
    ) {

        return orderService.getMyOrders(
                authentication.getName()
        );
    }
}