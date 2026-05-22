package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.AddressRequest;
import com.fooddelivery.fooddeliverybackend.dto.AddressResponse;

import com.fooddelivery.fooddeliverybackend.entity.Address;
import com.fooddelivery.fooddeliverybackend.entity.User;

import com.fooddelivery.fooddeliverybackend.repository.AddressRepository;
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

    // ADD ADDRESS
    public String addAddress(
            AddressRequest request,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Address address = Address.builder()

                .user(user)

                .fullName(request.getFullName())

                .phone(request.getPhone())

                .street(request.getStreet())

                .city(request.getCity())

                .state(request.getState())

                .pincode(request.getPincode())

                .build();

        addressRepository.save(address);

        return "Address Added Successfully";
    }

    // GET MY ADDRESSES
    public List<AddressResponse> myAddresses(
            String email
    ) {

        List<Address> addresses =
                addressRepository.findByUserEmail(email);

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
}