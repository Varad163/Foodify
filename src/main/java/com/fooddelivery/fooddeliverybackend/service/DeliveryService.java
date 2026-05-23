package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.DeliveryRequest;
import com.fooddelivery.fooddeliverybackend.entity.DeliveryPartner;
import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.OrderStatus;
import com.fooddelivery.fooddeliverybackend.entity.User;

import com.fooddelivery.fooddeliverybackend.repository.DeliveryPartnerRepository;
import com.fooddelivery.fooddeliverybackend.repository.OrderRepository;
import com.fooddelivery.fooddeliverybackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // REGISTER DELIVERY PARTNER
    public String register(
            DeliveryRequest request,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        DeliveryPartner partner =
                new DeliveryPartner();

        partner.setName(request.getName());
        partner.setPhone(request.getPhone());
        partner.setVehicleNumber(
                request.getVehicleNumber()
        );

        partner.setAvailable(true);

        partner.setUser(user);

        deliveryPartnerRepository.save(partner);

        return "Delivery Partner Registered";
    }

    // MY ORDERS
    public List<Order> getMyOrders(
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        DeliveryPartner partner =
                deliveryPartnerRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Partner not found"
                                )
                        );

        return orderRepository.findAll()
                .stream()
                .filter(order ->
                        order.getDeliveryPartner() != null &&
                                order.getDeliveryPartner()
                                        .getId()
                                        .equals(partner.getId())
                )
                .toList();
    }

    // PICKUP ORDER
    public String pickupOrder(
            Long orderId
    ) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(
                OrderStatus.OUT_FOR_DELIVERY
        );

        orderRepository.save(order);

        return "Order picked up";
    }

    // DELIVER ORDER
    public String deliverOrder(
            Long orderId
    ) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(
                OrderStatus.DELIVERED
        );

        orderRepository.save(order);

        return "Order delivered";
    }
}