package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.RestaurantRequest;
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

    // Create Restaurant
    @PostMapping("/create")
    public String createRestaurant(
            @RequestBody RestaurantRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return restaurantService.createRestaurant(
                request,
                email
        );
    }

    // Get All Restaurants
    @GetMapping("/all")
    public List<Restaurant> getAllRestaurants() {

        return restaurantService.getAllRestaurants();
    }
}