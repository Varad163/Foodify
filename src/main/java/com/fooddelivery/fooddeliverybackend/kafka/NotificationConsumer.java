package com.fooddelivery.fooddeliverybackend.kafka;

import com.fooddelivery.fooddeliverybackend.dto.NotificationMessage;
import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-group"
    )
    public void consume(OrderPlacedEvent event) {

        System.out.println(
                "NOTIFICATION RECEIVED -> "
                        + event.getOrderId()
        );

        NotificationMessage message =
                new NotificationMessage(
                        "Order Confirmed: " + event.getOrderId()
                );

        messagingTemplate.convertAndSend(
                "/topic/orders",
                message
        );
    }
}