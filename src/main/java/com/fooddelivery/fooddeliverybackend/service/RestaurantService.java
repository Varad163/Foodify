package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.RestaurantRequest;

import com.fooddelivery.fooddeliverybackend.entity.DeliveryPartner;
import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.OrderStatus;
import com.fooddelivery.fooddeliverybackend.entity.Restaurant;
import com.fooddelivery.fooddeliverybackend.entity.User;

import com.fooddelivery.fooddeliverybackend.repository.DeliveryPartnerRepository;
import com.fooddelivery.fooddeliverybackend.repository.OrderRepository;
import com.fooddelivery.fooddeliverybackend.repository.RestaurantRepository;
import com.fooddelivery.fooddeliverybackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

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

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    // =========================
    // CREATE RESTAURANT
    // =========================

    @CacheEvict(value = "restaurants", allEntries = true)

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

    // =========================
    // GET ALL RESTAURANTS
    // =========================

    @Cacheable(value = "restaurants")

    public List<Restaurant> getAllRestaurants() {

        return restaurantRepository.findAll();
    }

    // =========================
    // GET RESTAURANT ORDERS
    // =========================

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

        Restaurant restaurant = restaurantRepository
                .findByOwner(owner)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Restaurant not found"
                        )
                );

        return orderRepository
                .findByRestaurant(restaurant);
    }

    // =========================
    // CONFIRM ORDER
    // =========================

    public String confirmOrder(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        // FIND AVAILABLE DELIVERY PARTNER
        DeliveryPartner partner =
                deliveryPartnerRepository
                        .findFirstByAvailableTrue()
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "No delivery partner available"
                                )
                        );

        // ASSIGN DELIVERY PARTNER
        order.setDeliveryPartner(partner);

        // UPDATE STATUS
        order.setStatus(OrderStatus.CONFIRMED);

        // SAVE
        orderRepository.save(order);

        return "Order confirmed and delivery partner assigned";
    }

    // =========================
    // PREPARE ORDER
    // =========================

    public String prepareOrder(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(OrderStatus.PREPARING);

        orderRepository.save(order);

        return "Order is preparing";
    }

    // =========================
    // OUT FOR DELIVERY
    // =========================

    public String outForDelivery(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);

        orderRepository.save(order);

        return "Order is out for delivery";
    }

    // =========================
    // DELIVER ORDER
    // =========================

    public String deliverOrder(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(OrderStatus.DELIVERED);

        orderRepository.save(order);

        return "Order delivered";
    }

    // =========================
    // CANCEL ORDER
    // =========================

    public String cancelOrder(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return "Order cancelled";
    }
}