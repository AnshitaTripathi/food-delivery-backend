package com.fooddelivery.backend.entity;

import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {

    PLACED,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    private Set<OrderStatus> allowedNext;  

    static {
        PLACED.allowedNext = EnumSet.of(CONFIRMED, CANCELLED);
        CONFIRMED.allowedNext = EnumSet.of(PREPARING, CANCELLED);
        PREPARING.allowedNext = EnumSet.of(OUT_FOR_DELIVERY);
        OUT_FOR_DELIVERY.allowedNext = EnumSet.of(DELIVERED);
        DELIVERED.allowedNext = EnumSet.noneOf(OrderStatus.class);
        CANCELLED.allowedNext = EnumSet.noneOf(OrderStatus.class);
    }

    public boolean canTransitionTo(OrderStatus nextStatus) {
        return allowedNext.contains(nextStatus);
    }

    public Set<OrderStatus> getAllowedNext() {
        return allowedNext;
    }
}
