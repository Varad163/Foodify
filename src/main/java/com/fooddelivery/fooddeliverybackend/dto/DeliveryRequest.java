package com.fooddelivery.fooddeliverybackend.dto;

public class DeliveryRequest {

    private String name;

    private String phone;

    private String vehicleNumber;

    public DeliveryRequest() {
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}