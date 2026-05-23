package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.RestaurantRequest;
import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.OrderStatus;
import com.fooddelivery.fooddeliverybackend.entity.Restaurant;
import com.fooddelivery.fooddeliverybackend.entity.User;

import com.fooddelivery.fooddeliverybackend.repository.OrderRepository;
import com.fooddelivery.fooddeliverybackend.repository.RestaurantRepository;
import com.fooddelivery.fooddeliverybackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // =====================================
    // CREATE RESTAURANT
    // =====================================

    public String createRestaurant(
            RestaurantRequest request,
            String email
    ) {

        User owner = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setRating(0.0);

        restaurant.setOwner(owner);

        restaurantRepository.save(restaurant);

        return "Restaurant Created Successfully";
    }

    // =====================================
    // GET ALL RESTAURANTS
    // =====================================

    public List<Restaurant> getAllRestaurants() {

        return restaurantRepository.findAll();
    }

    // =====================================
    // GET RESTAURANT ORDERS
    // =====================================

    public List<Order> getRestaurantOrders(
            String email
    ) {

        User owner = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        Restaurant restaurant =
                restaurantRepository.findByOwner(owner)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Restaurant not found"
                                )
                        );

        return orderRepository.findByRestaurant(
                restaurant
        );
    }

    // =====================================
    // UPDATE ORDER STATUS
    // =====================================

    public String updateOrderStatus(
            Long orderId,
            String status
    ) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(
                OrderStatus.valueOf(status)
        );

        orderRepository.save(order);

        return "Order updated to " + status;
    }
}