package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.PaymentResponse;
import com.fooddelivery.fooddeliverybackend.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // PAY ORDER
    @PostMapping("/pay/{orderId}")
    public String payOrder(

            @PathVariable Long orderId,

            @RequestParam String method
    ) {

        return paymentService.payOrder(
                orderId,
                method
        );
    }

    // MY PAYMENTS
    @GetMapping("/my")
    public List<PaymentResponse> myPayments(
            Authentication authentication
    ) {

        return paymentService.myPayments(
                authentication.getName()
        );
    }
}