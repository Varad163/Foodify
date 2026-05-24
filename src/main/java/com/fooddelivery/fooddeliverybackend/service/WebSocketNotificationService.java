package com.fooddelivery.fooddeliverybackend.service;

import com.fooddelivery.fooddeliverybackend.dto.NotificationMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendOrderNotification(
            String message
    ) {

        NotificationMessage notification =
                new NotificationMessage(
                        message
                );

        messagingTemplate.convertAndSend(
                "/topic/orders",
                notification
        );
    }
}