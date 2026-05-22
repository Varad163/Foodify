package com.fooddelivery.fooddeliverybackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    // Amount
    private Double amount;

    // Payment Method
    private String method;

    // Status
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // Payment Time
    private LocalDateTime paidAt;
}