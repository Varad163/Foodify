package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.FoodItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository
        extends JpaRepository<FoodItem, Long> {

    List<FoodItem> findByRestaurantId(Long restaurantId);
}