package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.entity.OrderItem;

public class OrderItemResponseDto {

    private Long menuItemId;
    private String name;
    private double price;
    private int quantity;
    private double subTotal;

    public OrderItemResponseDto(
            Long menuItemId,
            String name,
            double price,
            int quantity,
            double subTotal
    ) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public static OrderItemResponseDto from(OrderItem item) {
        return new OrderItemResponseDto(
                item.getMenuItem().getId(),
                item.getMenuItem().getName(),
                item.getMenuItem().getPrice(),
                item.getQuantity(),
                item.getMenuItem().getPrice() * item.getQuantity()
        );
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    
}

