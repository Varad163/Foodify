package com.fooddelivery.fooddeliverybackend.config;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.TopicBuilder;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.listener.DefaultErrorHandler;

import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;

import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    // ==========================
    // MAIN TOPIC
    // ==========================

    @Bean
    public NewTopic orderTopic() {

        return TopicBuilder.name(
                        "order-events"
                )

                .partitions(3)

                .replicas(1)

                .build();
    }

    // ==========================
    // DEAD LETTER TOPIC
    // ==========================

    @Bean
    public NewTopic deadLetterTopic() {

        return TopicBuilder.name(
                "order-events-dlt"
        ).build();
    }

    // ==========================
    // ERROR HANDLER
    // ==========================

    @Bean
    public DefaultErrorHandler errorHandler(
            KafkaTemplate<Object, Object> kafkaTemplate
    ) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate
                );

        FixedBackOff fixedBackOff =
                new FixedBackOff(
                        2000L,
                        3
                );

        return new DefaultErrorHandler(
                recoverer,
                fixedBackOff
        );
    }
}