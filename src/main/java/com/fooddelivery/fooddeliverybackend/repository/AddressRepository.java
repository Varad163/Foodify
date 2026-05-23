package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.Address;
import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository
        extends JpaRepository<Address, Long> {

    Address findByOrder(Order order);

    List<Address> findByUser(User user);
}