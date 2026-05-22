package com.fooddelivery.fooddeliverybackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    private Long foodItemId;

    private Integer quantity;
}