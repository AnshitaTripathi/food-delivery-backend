package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.entity.Order;
import com.fooddelivery.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // USER order history (latest first)
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
