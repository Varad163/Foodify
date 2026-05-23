package com.fooddelivery.fooddeliverybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory connectionFactory
    ) {

        RedisTemplate<String, Object> template =
                new RedisTemplate<>();

        // CONNECTION
        template.setConnectionFactory(
                connectionFactory
        );

        // KEY SERIALIZER
        template.setKeySerializer(
                new StringRedisSerializer()
        );

        // VALUE SERIALIZER
        template.setValueSerializer(
                new GenericJackson2JsonRedisSerializer()
        );

        // HASH KEY SERIALIZER
        template.setHashKeySerializer(
                new StringRedisSerializer()
        );

        // HASH VALUE SERIALIZER
        template.setHashValueSerializer(
                new GenericJackson2JsonRedisSerializer()
        );

        template.afterPropertiesSet();

        return template;
    }
}