package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.ApiResponse;
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

    // ==========================
    // PLACE ORDER
    // ==========================

    @PostMapping("/place")
    public ApiResponse<String> placeOrder(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        String response =
                orderService.placeOrder(email);

        return new ApiResponse<>(
                true,
                response,
                null
        );
    }

    // ==========================
    // MY ORDERS
    // ==========================

    @GetMapping("/my")
    public ApiResponse<List<OrderResponse>>
    myOrders(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        List<OrderResponse> orders =
                orderService.getMyOrders(email);

        return new ApiResponse<>(
                true,
                "Orders fetched successfully",
                orders
        );
    }

    // ==========================
    // UPDATE ORDER STATUS
    // ==========================

    @PutMapping("/status/{orderId}")
    public ApiResponse<String>
    updateOrderStatus(

            @PathVariable Long orderId,

            @RequestParam String status
    ) {

        String response =
                orderService.updateOrderStatus(
                        orderId,
                        status
                );

        return new ApiResponse<>(
                true,
                response,
                null
        );
    }

    // ==========================
    // ORDER DETAILS
    // ==========================

    @GetMapping("/details/{orderId}")
    public ApiResponse<OrderDetailsResponse>
    getOrderDetails(

            @PathVariable Long orderId
    ) {

        OrderDetailsResponse response =
                orderService.getOrderDetails(
                        orderId
                );

        return new ApiResponse<>(
                true,
                "Order details fetched successfully",
                response
        );
    }
}