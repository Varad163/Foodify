package com.fooddelivery.fooddeliverybackend.kafka;

import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-group"
    )
    public void sendNotification(
            OrderPlacedEvent event
    ) {

        System.out.println(
                "NOTIFICATION SERVICE -> " +
                        "Sending order confirmation for Order ID: "
                        + event.getOrderId()
        );
    }
}