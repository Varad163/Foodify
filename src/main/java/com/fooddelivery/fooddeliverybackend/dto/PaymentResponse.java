package com.fooddelivery.fooddeliverybackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private Double amount;

    private String paymentMethod;

    private String status;
}