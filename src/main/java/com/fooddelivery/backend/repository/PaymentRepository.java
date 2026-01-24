package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.entity.Order;
import com.fooddelivery.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Fetch payment for a specific order
    Optional<Payment> findByOrder(Order order);
}

