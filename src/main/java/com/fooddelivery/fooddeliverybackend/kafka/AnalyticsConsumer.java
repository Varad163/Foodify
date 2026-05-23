package com.fooddelivery.fooddeliverybackend.kafka;

import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

@Service
public class AnalyticsConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "analytics-group"
    )
    public void updateAnalytics(
            OrderPlacedEvent event
    ) {

        System.out.println(
                "ANALYTICS SERVICE -> " +
                        "Updating metrics for Order ID: "
                        + event.getOrderId()
        );
    }
}