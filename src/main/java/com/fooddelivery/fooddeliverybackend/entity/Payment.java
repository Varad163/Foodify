package com.fooddelivery.fooddeliverybackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Order
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Double amount;

    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}