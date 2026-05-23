package com.fooddelivery.fooddeliverybackend.kafka;

import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer2 {

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-group"
    )
    public void consume(
            OrderPlacedEvent event
    ) {

        System.out.println(
                "Consumer 2 processed order: "
                        + event.getOrderId()
        );
    }
}