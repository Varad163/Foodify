package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.AddressResponse;

import com.fooddelivery.fooddeliverybackend.entity.Address;
import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.User;

import com.fooddelivery.fooddeliverybackend.repository.AddressRepository;
import com.fooddelivery.fooddeliverybackend.repository.OrderRepository;
import com.fooddelivery.fooddeliverybackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // ==========================
    // ADD ADDRESS
    // ==========================

    @CacheEvict(
            value = "addresses",
            key = "#email"
    )
    public String addAddress(
            String email,
            Address address
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        address.setUser(user);

        addressRepository.save(address);

        return "Address Added Successfully";
    }

    // ==========================
    // GET MY ADDRESSES
    // ==========================

    @Cacheable(
            value = "addresses",
            key = "#email"
    )
    public List<AddressResponse> myAddresses(
            String email
    ) {

        System.out.println(
                "Fetching addresses from DB..."
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        List<Address> addresses =
                addressRepository.findByUser(user);

        return addresses.stream()

                .map(address -> AddressResponse.builder()

                        .id(address.getId())

                        .fullName(address.getFullName())

                        .phone(address.getPhone())

                        .street(address.getStreet())

                        .city(address.getCity())

                        .state(address.getState())

                        .pincode(address.getPincode())

                        .build())

                .collect(Collectors.toList());
    }

    // ==========================
    // GET ORDER ADDRESS
    // ==========================

    @Cacheable(
            value = "orderAddresses",
            key = "#orderId"
    )
    public AddressResponse getOrderAddress(
            Long orderId
    ) {

        System.out.println(
                "Fetching order address from DB..."
        );

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );

        Address address =
                addressRepository.findByOrder(order);

        return AddressResponse.builder()

                .id(address.getId())

                .fullName(address.getFullName())

                .phone(address.getPhone())

                .street(address.getStreet())

                .city(address.getCity())

                .state(address.getState())

                .pincode(address.getPincode())

                .build();
    }
}