package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.FoodItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository
        extends JpaRepository<FoodItem, Long> {
}