package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.AddressResponse;
import com.fooddelivery.fooddeliverybackend.entity.Address;
import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.User;
import com.fooddelivery.fooddeliverybackend.repository.AddressRepository;
import com.fooddelivery.fooddeliverybackend.repository.OrderRepository;
import com.fooddelivery.fooddeliverybackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    // ADD ADDRESS
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

    // GET MY ADDRESSES
    public List<AddressResponse> myAddresses(
            String email
    ) {

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

    // GET ADDRESS OF ORDER
    public AddressResponse getOrderAddress(
            Long orderId
    ) {

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