package com.fooddelivery.backend.repository;

import com.fooddelivery.backend.entity.Cart;
import com.fooddelivery.backend.entity.CartItem;
import com.fooddelivery.backend.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndMenuItem(Cart cart, MenuItem menuItem);
}
    