package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.FoodRequest;

import com.fooddelivery.fooddeliverybackend.entity.FoodItem;
import com.fooddelivery.fooddeliverybackend.entity.Restaurant;

import com.fooddelivery.fooddeliverybackend.repository.FoodRepository;
import com.fooddelivery.fooddeliverybackend.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // ==========================
    // ADD FOOD
    // ==========================

    @CacheEvict(
            value = {
                    "foods",
                    "restaurantFoods"
            },
            allEntries = true
    )
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

    // ==========================
    // GET ALL FOOD WITH PAGINATION
    // ==========================

    @Cacheable(value = "foods")
    public Page<FoodItem> getAllFood(

            int page,
            int size,
            String sortBy
    ) {

        System.out.println(
                "Fetching foods from DB..."
        );

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        return foodRepository.findAll(pageable);
    }

    // ==========================
    // GET FOOD BY RESTAURANT
    // WITH PAGINATION
    // ==========================

    @Cacheable(
            value = "restaurantFoods",
            key = "#restaurantId + '-' + #page + '-' + #size"
    )
    public Page<FoodItem> getFoodByRestaurant(

            Long restaurantId,
            int page,
            int size
    ) {

        System.out.println(
                "Fetching restaurant foods from DB..."
        );

        Pageable pageable =
                PageRequest.of(page, size);

        return foodRepository.findByRestaurantId(
                restaurantId,
                pageable
        );
    }
}