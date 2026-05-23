package com.fooddelivery.fooddeliverybackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderDetailsResponse {

    private Long orderId;

    private Double totalAmount;

    private String status;

    private String paymentMethod;

    private String paymentStatus;

    private String city;

    private String street;

    private List<OrderItemResponse> items;
}