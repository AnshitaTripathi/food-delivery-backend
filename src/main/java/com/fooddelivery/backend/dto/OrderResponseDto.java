package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.entity.Order;
import com.fooddelivery.backend.entity.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseDto {

    private Long orderId;
    private OrderStatus status;
    private List<OrderItemResponseDto> items;
    private double totalAmount;

    public OrderResponseDto(
            Long orderId,
            OrderStatus status,
            List<OrderItemResponseDto> items,
            double totalAmount
    ) {
        this.orderId = orderId;
        this.status = status;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public static OrderResponseDto from(Order order) {

        List<OrderItemResponseDto> itemDtos =
                order.getItems()
                        .stream()
                        .map(OrderItemResponseDto::from)
                        .collect(Collectors.toList());

        double total = order.getItems()
                .stream()
                .mapToDouble(i ->
                        i.getMenuItem().getPrice() * i.getQuantity()
                )
                .sum();

        return new OrderResponseDto(
                order.getId(),
                order.getStatus(),
                itemDtos,
                total
        );
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDto> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    
}

