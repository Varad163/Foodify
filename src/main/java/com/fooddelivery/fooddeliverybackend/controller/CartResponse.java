package com.fooddelivery.fooddeliverybackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartResponse {

    private Long foodId;

    private String foodName;

    private Double price;

    private Integer quantity;

    private String restaurantName;
}