package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.PaymentOrderResponseDto;
import com.fooddelivery.backend.entity.*;
import com.fooddelivery.backend.exceptions.ResourceNotFoundException;
import com.fooddelivery.backend.repository.OrderRepository;
import com.fooddelivery.backend.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.Order;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final RazorpayClient razorpayClient;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            RazorpayClient razorpayClient
    ) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.razorpayClient = razorpayClient;
    }

    // ================= CREATE RAZORPAY ORDER =================
    @Transactional
    public PaymentOrderResponseDto createRazorpayOrder(Long orderId) throws Exception {

        com.fooddelivery.backend.entity.Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Order not found"));

        // Prevent duplicate payment
        paymentRepository.findByOrder(order).ifPresent(p -> {
            throw new IllegalStateException("Payment already initiated for this order");
        });

        int amountInPaise = (int) (order.getTotalAmount() * 100);

        JSONObject options = new JSONObject();
        options.put("amount", amountInPaise);
        options.put("currency", "INR");
        options.put("receipt", "order_" + order.getId());

       //  Razorpay Order
    Order razorpayOrder = razorpayClient.orders.create(options);

    Payment payment = new Payment();
    payment.setOrder(order);
    payment.setAmount(order.getTotalAmount());
    payment.setMethod(PaymentMethod.RAZORPAY);
    payment.setStatus(PaymentStatus.INITIATED);
    payment.setRazorpayOrderId(razorpayOrder.get("id"));

    paymentRepository.save(payment);

    return new PaymentOrderResponseDto(
            razorpayOrder.get("id"),
            order.getTotalAmount(),
            "INR"
    );

    }

    // ================= CASH ON DELIVERY =================
    @Transactional
    public Payment createCodPayment(Long orderId) {

        com.fooddelivery.backend.entity.Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Order not found"));

        paymentRepository.findByOrder(order).ifPresent(p -> {
            throw new IllegalStateException("Payment already exists for this order");
        });

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(PaymentMethod.COD);
        payment.setStatus(PaymentStatus.SUCCESS);

        order.setStatus(OrderStatus.CONFIRMED);

        paymentRepository.save(payment);
        orderRepository.save(order);

        return payment;
    }

    // ================= GET PAYMENT BY ORDER =================
    public Payment getPaymentByOrder(Long orderId) {

        com.fooddelivery.backend.entity.Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Order not found"));

        return paymentRepository.findByOrder(order)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));
    }
}
