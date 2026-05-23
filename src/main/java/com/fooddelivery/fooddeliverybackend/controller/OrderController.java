package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.OrderDetailsResponse;
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

        String email =
                authentication.getName();

        return orderService.placeOrder(email);
    }

    // MY ORDERS
    @GetMapping("/my")

    public List<OrderResponse> myOrders(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return orderService.getMyOrders(email);
    }

    // UPDATE ORDER STATUS
    @PutMapping("/status/{orderId}")

    public String updateOrderStatus(

            @PathVariable Long orderId,

            @RequestParam String status
    ) {

        return orderService.updateOrderStatus(
                orderId,
                status
        );
    }

    // ORDER DETAILS
    @GetMapping("/details/{orderId}")

    public OrderDetailsResponse getOrderDetails(
            @PathVariable Long orderId
    ) {

        return orderService.getOrderDetails(
                orderId
        );
    }
}