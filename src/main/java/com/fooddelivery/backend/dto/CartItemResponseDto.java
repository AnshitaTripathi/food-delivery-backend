package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.entity.CartItem;
import com.fooddelivery.backend.entity.MenuItem;

public class CartItemResponseDto {

    private Long menuItemId;
    private String name;
    private double price;
    private int quantity;
    private double subTotal;

    public CartItemResponseDto() {}

    public CartItemResponseDto(
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

    //  REQUIRED MAPPER
    public static CartItemResponseDto from(CartItem cartItem) {
        MenuItem menuItem = cartItem.getMenuItem();

        return new CartItemResponseDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getPrice(),
                cartItem.getQuantity(),
                menuItem.getPrice() * cartItem.getQuantity()
        );
    }

    // getters
    public Long getMenuItemId() {
        return menuItemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }
}
