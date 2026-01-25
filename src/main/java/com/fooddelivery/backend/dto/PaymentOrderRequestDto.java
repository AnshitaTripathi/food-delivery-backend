package com.fooddelivery.backend.dto;

import jakarta.validation.constraints.NotNull;

public class PaymentOrderRequestDto {

    @NotNull
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

