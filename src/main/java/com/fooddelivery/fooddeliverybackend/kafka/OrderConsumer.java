package com.fooddelivery.fooddeliverybackend.kafka;

import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "food-group"
    )
    public void consume(
            OrderPlacedEvent event
    ) {

        System.out.println(
                "Order event received: " + event
        );
    }
}