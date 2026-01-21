package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.entity.Cart;
import com.fooddelivery.backend.entity.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponseDto {

    private Long cartId;
    private List<CartItemResponseDto> items;
    private double totalAmount;

    public CartResponseDto() {
    }

    public CartResponseDto(Long cartId,
                           List<CartItemResponseDto> items,
                           double totalAmount) {
        this.cartId = cartId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getCartId() {
        return cartId;
    }

    public List<CartItemResponseDto> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

   
    public static CartResponseDto from(Cart cart) {

        List<CartItemResponseDto> itemDtos =
                cart.getItems()
                        .stream()
                        .map(CartItemResponseDto::from)
                        .collect(Collectors.toList());

        double total = cart.getItems()
                .stream()
                .mapToDouble(item ->
                        item.getMenuItem().getPrice() * item.getQuantity()
                )
                .sum();

        return new CartResponseDto(
                cart.getId(),
                itemDtos,
                total
        );  
    }
}
