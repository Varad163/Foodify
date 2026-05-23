package com.fooddelivery.fooddeliverybackend.kafka;

import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final String TOPIC =
            "order-events";

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent>
            kafkaTemplate;

    public void sendOrderEvent(
            OrderPlacedEvent event
    ) {

        kafkaTemplate.send(
                TOPIC,
                event
        );

        System.out.println(
                "Order event sent: " + event
        );
    }
}