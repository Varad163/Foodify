package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.PaymentResponse;

import com.fooddelivery.fooddeliverybackend.entity.Order;
import com.fooddelivery.fooddeliverybackend.entity.Payment;
import com.fooddelivery.fooddeliverybackend.entity.PaymentStatus;

import com.fooddelivery.fooddeliverybackend.repository.OrderRepository;
import com.fooddelivery.fooddeliverybackend.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    // PAY ORDER
    public String payOrder(
            Long orderId,
            String method
    ) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        Payment existingPayment =
                paymentRepository.findByOrder(order);

        if (existingPayment != null) {

            return "Payment already done";
        }

        Payment payment = Payment.builder()

                .order(order)

                .amount(order.getTotalAmount())

                .paymentMethod(method)

                .status(PaymentStatus.SUCCESS)

                .build();

        paymentRepository.save(payment);

        return "Payment Successful";
    }

    // MY PAYMENTS
    public List<PaymentResponse> myPayments(
            String email
    ) {

        List<Payment> payments =
                paymentRepository
                        .findByOrderUserEmail(email);

        return payments.stream()

                .map(payment -> PaymentResponse.builder()

                        .paymentId(payment.getId())

                        .orderId(
                                payment.getOrder().getId()
                        )

                        .amount(
                                payment.getAmount()
                        )

                        .paymentMethod(
                                payment.getPaymentMethod()
                        )

                        .status(
                                payment.getStatus().name()
                        )

                        .build())

                .collect(Collectors.toList());
    }
}