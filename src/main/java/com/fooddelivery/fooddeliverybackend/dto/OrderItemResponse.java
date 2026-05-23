package com.fooddelivery.fooddeliverybackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItemResponse {

    private String foodName;

    private Integer quantity;

    private Double price;
}