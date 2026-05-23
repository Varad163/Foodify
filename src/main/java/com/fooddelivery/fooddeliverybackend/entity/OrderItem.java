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

    @ManyToOne
    @JoinColumn(name = "order_id")

    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id")

    private FoodItem foodItem;

    private Integer quantity;

    private Double price;
}