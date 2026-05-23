package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.Restaurant;
import com.fooddelivery.fooddeliverybackend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository
        extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByOwner(User owner);
}