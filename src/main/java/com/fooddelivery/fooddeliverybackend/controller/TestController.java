package com.fooddelivery.fooddeliverybackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {

        return "Food Delivery Backend Running 🚀";
    }

    @GetMapping("/profile")
    public String profile() {

        return "Customer Profile API";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {

        return "Admin Dashboard";
    }

    @GetMapping("/restaurant/dashboard")
    public String restaurantDashboard() {

        return "Restaurant Owner Dashboard";
    }
}