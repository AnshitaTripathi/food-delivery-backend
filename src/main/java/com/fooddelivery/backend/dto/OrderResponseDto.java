package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.entity.Order;
import com.fooddelivery.backend.entity.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseDto {

    private Long orderId;
    private OrderStatus status;
    private double totalAmount;
    private List<OrderItemResponseDto> items;

    public OrderResponseDto(
            Long orderId,
            OrderStatus status,
            double totalAmount,
            List<OrderItemResponseDto> items
    ) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public static OrderResponseDto from(Order order) {

        return new OrderResponseDto(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getItems()
                        .stream()
                        .map(OrderItemResponseDto::from)
                        .collect(Collectors.toList())
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

    public List<OrderItemResponseDto> getItems() {
        return items;
    }
}
