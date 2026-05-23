package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.Restaurant;
import com.fooddelivery.fooddeliverybackend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByRestaurant(Restaurant restaurant);
}