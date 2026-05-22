package com.fooddelivery.fooddeliverybackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Order reference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Snapshot data
    private String foodName;

    private Double price;

    private Integer quantity;
}