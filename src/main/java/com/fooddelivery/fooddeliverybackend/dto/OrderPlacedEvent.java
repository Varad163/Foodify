package com.fooddelivery.fooddeliverybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {

    private Long orderId;

    private Long userId;

    private Long restaurantId;

    private Double amount;
}