package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.FoodRequest;
import com.fooddelivery.fooddeliverybackend.entity.FoodItem;
import com.fooddelivery.fooddeliverybackend.service.FoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")

public class FoodController {

    @Autowired
    private FoodService foodService;

    // Add Food
    @PostMapping("/add")
    public String addFood(
            @RequestBody FoodRequest request
    ) {

        return foodService.addFood(request);
    }

    // Get All Food
    @GetMapping("/all")
    public List<FoodItem> getAllFood() {

        return foodService.getAllFood();
    }

    // Get Food By Restaurant
    @GetMapping("/restaurant/{id}")
    public List<FoodItem> getFoodByRestaurant(
            @PathVariable Long id
    ) {

        return foodService
                .getFoodByRestaurant(id);
    }
}