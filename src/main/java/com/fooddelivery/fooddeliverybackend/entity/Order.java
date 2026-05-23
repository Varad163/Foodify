package com.fooddelivery.fooddeliverybackend.entity;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CUSTOMER
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // RESTAURANT
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // DELIVERY PARTNER
    @ManyToOne
    @JoinColumn(name = "delivery_partner_id")
    private DeliveryPartner deliveryPartner;

    // TOTAL
    private Double totalAmount;

    // STATUS
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // CREATED TIME
    private LocalDateTime createdAt;
}