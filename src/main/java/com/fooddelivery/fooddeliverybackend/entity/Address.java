package com.fooddelivery.fooddeliverybackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;

    private String phone;

    private String street;

    private String city;

    private String state;

    private String pincode;
}