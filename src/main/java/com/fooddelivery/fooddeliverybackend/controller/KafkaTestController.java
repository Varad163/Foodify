package com.fooddelivery.fooddeliverybackend.controller;

import com.fooddelivery.fooddeliverybackend.dto.OrderPlacedEvent;
import com.fooddelivery.fooddeliverybackend.kafka.OrderProducer;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/send")
    public String sendEvent() {

        OrderPlacedEvent event =
                new OrderPlacedEvent(
                        1L,
                        101L,
                        201L,
                        500.0
                );

        orderProducer.sendOrderEvent(event);

        return "Kafka event sent successfully";
    }
}