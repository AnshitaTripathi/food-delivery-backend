package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.entity.Order;
import com.fooddelivery.backend.entity.OrderStatus;

public class OrderSummaryDto {

    private Long orderId;
    private OrderStatus status;
    private double totalAmount;

    public OrderSummaryDto(Long orderId, OrderStatus status, double totalAmount) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public static OrderSummaryDto from(Order order) {
        return new OrderSummaryDto(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount()
        );
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

