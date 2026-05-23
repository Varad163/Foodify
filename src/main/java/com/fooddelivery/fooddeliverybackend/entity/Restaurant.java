package com.fooddelivery.fooddeliverybackend.entity;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "restaurants")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}