package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.DeliveryPartner;
import com.fooddelivery.fooddeliverybackend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryPartnerRepository
        extends JpaRepository<DeliveryPartner, Long> {

    Optional<DeliveryPartner> findByUser(User user);

    Optional<DeliveryPartner> findFirstByAvailableTrue();
}