package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.ApiResponse;
import com.fooddelivery.fooddeliverybackend.dto.FoodRequest;

import com.fooddelivery.fooddeliverybackend.entity.FoodItem;

import com.fooddelivery.fooddeliverybackend.service.FoodService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    // ==========================
    // ADD FOOD
    // ==========================

    @PostMapping("/add")
    public ApiResponse<String> addFood(
            @RequestBody FoodRequest request
    ) {

        String response =
                foodService.addFood(request);

        return new ApiResponse<>(
                true,
                response,
                null
        );
    }

    // ==========================
    // GET ALL FOOD
    // WITH PAGINATION
    // ==========================

    @GetMapping("/all")
    public ApiResponse<Page<FoodItem>> getAllFood(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy
    ) {

        Page<FoodItem> foods =
                foodService.getAllFood(
                        page,
                        size,
                        sortBy
                );

        return new ApiResponse<>(
                true,
                "Foods fetched successfully",
                foods
        );
    }

    // ==========================
    // GET FOOD BY RESTAURANT
    // WITH PAGINATION
    // ==========================

    @GetMapping("/restaurant/{id}")
    public ApiResponse<Page<FoodItem>>
    getFoodByRestaurant(

            @PathVariable Long id,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size
    ) {

        Page<FoodItem> foods =
                foodService.getFoodByRestaurant(
                        id,
                        page,
                        size
                );

        return new ApiResponse<>(
                true,
                "Restaurant foods fetched successfully",
                foods
        );
    }
}