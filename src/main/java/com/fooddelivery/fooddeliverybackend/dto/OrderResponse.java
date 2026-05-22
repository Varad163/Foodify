package com.fooddelivery.fooddeliverybackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderResponse {

    private Long orderId;

    private Double totalAmount;

    private String status;

    private LocalDateTime createdAt;
}