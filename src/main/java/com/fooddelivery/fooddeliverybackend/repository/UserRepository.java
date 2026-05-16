package com.fooddelivery.fooddeliverybackend.repository;

import com.fooddelivery.fooddeliverybackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long> {

}