package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.FoodRequest;
import com.fooddelivery.fooddeliverybackend.entity.FoodItem;
import com.fooddelivery.fooddeliverybackend.entity.Restaurant;
import com.fooddelivery.fooddeliverybackend.repository.FoodRepository;
import com.fooddelivery.fooddeliverybackend.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public String addFood(
            FoodRequest request
    ) {

        Restaurant restaurant =
                restaurantRepository
                        .findById(request.getRestaurantId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Restaurant not found"
                                )
                        );

        FoodItem food = new FoodItem();

        food.setName(request.getName());

        food.setDescription(
                request.getDescription()
        );

        food.setPrice(request.getPrice());

        food.setVeg(request.getVeg());

        food.setAvailable(true);

        food.setRestaurant(restaurant);

        foodRepository.save(food);

        return "Food Added Successfully";
    }
}