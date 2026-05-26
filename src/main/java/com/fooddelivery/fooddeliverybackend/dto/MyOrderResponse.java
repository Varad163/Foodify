package com.fooddelivery.fooddeliverybackend.dto;

import com.fooddelivery.fooddeliverybackend.entity.OrderStatus;

public class MyOrderResponse {

    private Long orderId;

    private Double totalAmount;

    private OrderStatus status;

    private String restaurantName;

    private String deliveryPartnerName;

    public MyOrderResponse() {
    }

    public MyOrderResponse(
            Long orderId,
            Double totalAmount,
            OrderStatus status,
            String restaurantName,
            String deliveryPartnerName
    ) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.restaurantName = restaurantName;
        this.deliveryPartnerName = deliveryPartnerName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDeliveryPartnerName() {
        return deliveryPartnerName;
    }

    public void setDeliveryPartnerName(String deliveryPartnerName) {
        this.deliveryPartnerName = deliveryPartnerName;
    }
}