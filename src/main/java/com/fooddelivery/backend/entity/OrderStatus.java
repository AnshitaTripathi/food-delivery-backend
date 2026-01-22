package com.fooddelivery.backend.entity;

public enum OrderStatus {
    PLACED,          // when user places order
    CONFIRMED,       // admin accepts
    PREPARING,       // kitchen preparing
    OUT_FOR_DELIVERY,// delivery partner assigned
    DELIVERED,       // completed
    CANCELLED        // optional
}

