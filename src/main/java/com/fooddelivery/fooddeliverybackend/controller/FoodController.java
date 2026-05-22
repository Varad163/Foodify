package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.FoodRequest;
import com.fooddelivery.fooddeliverybackend.service.FoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")

public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/add")
    public String addFood(
            @RequestBody FoodRequest request
    ) {

        return foodService.addFood(request);
    }
}