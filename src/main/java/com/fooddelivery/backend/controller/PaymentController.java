package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.entity.Payment;
import com.fooddelivery.backend.entity.PaymentMethod;
import com.fooddelivery.backend.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment APIs", description = "APIs for handling order payments")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //  INITIATE PAYMENT 
    @Operation(summary = "Initiate payment for an order")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/initiate")
    public ResponseEntity<Payment> initiatePayment(
            @RequestParam Long orderId,
            @RequestParam PaymentMethod method
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.initiatePayment(orderId, method));
    }

    //  CONFIRM PAYMENT (UPI / CARD) 
    @Operation(summary = "Confirm payment (UPI / CARD)")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/confirm")
    public ResponseEntity<Payment> confirmPayment(
            @RequestParam Long orderId,
            @RequestParam boolean success
    ) {
        return ResponseEntity.ok(
                paymentService.confirmPayment(orderId, success)
        );
    }

    //  GET PAYMENT BY ORDER 
    @Operation(summary = "Get payment details for an order")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrder(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                paymentService.getPaymentByOrder(orderId)
        );
    }
}
