package com.fooddelivery.fooddeliverybackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodRequest {

    private String name;

    private String description;

    private Double price;

    private Boolean veg;

    private Long restaurantId;
}