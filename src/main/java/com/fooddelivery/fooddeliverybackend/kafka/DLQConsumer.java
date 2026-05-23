package com.fooddelivery.fooddeliverybackend.kafka;

import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

@Service
public class DLQConsumer {

    @KafkaListener(
            topics = "order-events-dlt",
            groupId = "dlq-group"
    )
    public void consumeDeadLetter(
            OrderPlacedEvent event
    ) {

        System.out.println(
                "DLQ RECEIVED FAILED EVENT -> "
                        + event
        );
    }
}