package com.fooddelivery.fooddeliverybackend.exception;

public class RestaurantNotFoundException
        extends RuntimeException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}