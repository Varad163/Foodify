package com.fooddelivery.fooddeliverybackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    private Long foodId;

    private Integer quantity;
}