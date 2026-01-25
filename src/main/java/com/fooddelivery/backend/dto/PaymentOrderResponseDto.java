package com.fooddelivery.backend.dto;

public class PaymentOrderResponseDto {

    private String razorpayOrderId;
    private double amount;
    private String currency;

    public PaymentOrderResponseDto(String razorpayOrderId,
                                   double amount,
                                   String currency) {
        this.razorpayOrderId = razorpayOrderId;
        this.amount = amount;
        this.currency = currency;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    
}

