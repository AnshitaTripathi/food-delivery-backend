package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.CartItemRequestDto;
import com.fooddelivery.backend.dto.CartResponseDto;
import com.fooddelivery.backend.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart APIs", description = "APIs for managing user cart")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    //  ADD ITEM TO CART 
    @Operation(summary = "Add item to cart")
    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItemToCart(
            @Valid @RequestBody CartItemRequestDto dto
    ) {
        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        CartResponseDto response =
                cartService.addItemToCart(userEmail, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //  VIEW CART 
    @Operation(summary = "Get current user's cart")
    @GetMapping
    public ResponseEntity<CartResponseDto> getMyCart() {

        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                cartService.getCartByUser(userEmail)
        );
    }
}
