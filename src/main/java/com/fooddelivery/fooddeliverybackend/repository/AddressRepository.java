package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository
        extends JpaRepository<Address, Long> {

    List<Address> findByUserEmail(
            String email
    );
}