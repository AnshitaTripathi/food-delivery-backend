package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.OrderResponseDto;
import com.fooddelivery.backend.entity.OrderStatus;
import com.fooddelivery.backend.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order APIs", description = "APIs for managing orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ================= PLACE ORDER =================
    @Operation(summary = "Place order from cart")
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder() {

        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        OrderResponseDto order =
                orderService.placeOrder(userEmail);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }

    // ================= USER ORDER HISTORY =================
    @Operation(summary = "Get current user's orders")
    @GetMapping("/me")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders() {

        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                orderService.getOrdersByUser(userEmail)
        );
    }

    // ================= ADMIN: ALL ORDERS =================
    @Operation(summary = "Get all orders (ADMIN)")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    // ================= ADMIN: UPDATE STATUS =================
    @Operation(summary = "Update order status (ADMIN)")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, status)
        );
    }
}
