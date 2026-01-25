package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.PaymentOrderResponseDto;
import com.fooddelivery.backend.entity.Payment;
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

    //  RAZORPAY 
    @Operation(summary = "Create Razorpay order")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/razorpay/create")
    public ResponseEntity<PaymentOrderResponseDto> createRazorpayOrder(
            @RequestParam Long orderId
    ) throws Exception {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createRazorpayOrder(orderId));
    }

    //  CASH ON DELIVERY 
    @Operation(summary = "Create COD payment")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cod")
    public ResponseEntity<Payment> createCodPayment(
            @RequestParam Long orderId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createCodPayment(orderId));
    }

    //  GET PAYMENT 
    @Operation(summary = "Get payment by order")
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
