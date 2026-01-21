package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.entity.Cart;
import com.fooddelivery.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}

