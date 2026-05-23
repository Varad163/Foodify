package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.RestaurantRequest;

import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.Restaurant;

import com.fooddelivery.fooddeliverybackend.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")

public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // =========================
    // CREATE RESTAURANT
    // =========================

    @PostMapping("/create")

    public String createRestaurant(
            @RequestBody RestaurantRequest request,
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return restaurantService
                .createRestaurant(
                        request,
                        email
                );
    }

    // =========================
    // GET ALL RESTAURANTS
    // =========================

    @GetMapping("/all")

    public List<Restaurant> getAllRestaurants() {

        return restaurantService
                .getAllRestaurants();
    }

    // =========================
    // GET RESTAURANT ORDERS
    // =========================

    @GetMapping("/orders")

    public List<Order> getRestaurantOrders(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return restaurantService
                .getRestaurantOrders(email);
    }

    // =========================
    // CONFIRM ORDER
    // =========================

    @PutMapping("/confirm/{orderId}")

    public String confirmOrder(
            @PathVariable Long orderId
    ) {

        return restaurantService
                .confirmOrder(orderId);
    }

    // =========================
    // PREPARE ORDER
    // =========================

    @PutMapping("/prepare/{orderId}")

    public String prepareOrder(
            @PathVariable Long orderId
    ) {

        return restaurantService
                .prepareOrder(orderId);
    }

    // =========================
    // OUT FOR DELIVERY
    // =========================

    @PutMapping("/out-for-delivery/{orderId}")

    public String outForDelivery(
            @PathVariable Long orderId
    ) {

        return restaurantService
                .outForDelivery(orderId);
    }

    // =========================
    // DELIVER ORDER
    // =========================

    @PutMapping("/deliver/{orderId}")

    public String deliverOrder(
            @PathVariable Long orderId
    ) {

        return restaurantService
                .deliverOrder(orderId);
    }

    // =========================
    // CANCEL ORDER
    // =========================

    @PutMapping("/cancel/{orderId}")

    public String cancelOrder(
            @PathVariable Long orderId
    ) {

        return restaurantService
                .cancelOrder(orderId);
    }
}