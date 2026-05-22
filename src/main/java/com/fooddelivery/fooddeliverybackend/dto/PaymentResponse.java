package com.fooddelivery.fooddeliverybackend.dto;

import com.fooddelivery.fooddeliverybackend.entity.PaymentStatus;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private Double amount;

    private String method;

    private PaymentStatus status;

    private LocalDateTime paidAt;
}