package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.DeliveryRequest;
import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.service.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // REGISTER
    @PostMapping("/register")
    public String register(
            @RequestBody DeliveryRequest request,
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return deliveryService.register(
                request,
                email
        );
    }

    // MY ORDERS
    @GetMapping("/my-orders")
    public List<Order> myOrders(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return deliveryService.getMyOrders(
                email
        );
    }

    // PICKUP ORDER
    @PutMapping("/pickup/{orderId}")
    public String pickupOrder(
            @PathVariable Long orderId
    ) {

        return deliveryService.pickupOrder(
                orderId
        );
    }

    // DELIVER ORDER
    @PutMapping("/deliver/{orderId}")
    public String deliverOrder(
            @PathVariable Long orderId
    ) {

        return deliveryService.deliverOrder(
                orderId
        );
    }
}