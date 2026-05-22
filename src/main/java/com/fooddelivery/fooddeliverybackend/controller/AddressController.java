package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.AddressRequest;
import com.fooddelivery.fooddeliverybackend.dto.AddressResponse;

import com.fooddelivery.fooddeliverybackend.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")

public class AddressController {

    @Autowired
    private AddressService addressService;

    // ADD ADDRESS
    @PostMapping("/add")
    public String addAddress(

            @RequestBody AddressRequest request,

            Authentication authentication
    ) {

        return addressService.addAddress(
                request,
                authentication.getName()
        );
    }

    // GET MY ADDRESSES
    @GetMapping("/my")
    public List<AddressResponse> myAddresses(
            Authentication authentication
    ) {

        return addressService.myAddresses(
                authentication.getName()
        );
    }
}