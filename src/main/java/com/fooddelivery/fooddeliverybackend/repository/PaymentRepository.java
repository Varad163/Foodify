package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.Payment;
import com.fooddelivery.fooddeliverybackend.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderUserEmail(
            String email
    );

    Payment findByOrder(Order order);
}