package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.OrderItemResponseDto;
import com.fooddelivery.backend.dto.OrderResponseDto;
import com.fooddelivery.backend.entity.*;
import com.fooddelivery.backend.exceptions.ResourceNotFoundException;
import com.fooddelivery.backend.repository.CartRepository;
import com.fooddelivery.backend.repository.OrderRepository;
import com.fooddelivery.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    //  PLACE ORDER 
    @Transactional
    public OrderResponseDto placeOrder(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart is empty"));

        if (cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);

        // Convert cart items → order items
        List<OrderItem> orderItems = cart.getItems()
                .stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setMenuItem(cartItem.getMenuItem());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getMenuItem().getPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.getItems().addAll(orderItems);

        double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // Clear cart AFTER successful order
        cart.getItems().clear();
        cartRepository.save(cart);

        return OrderResponseDto.from(savedOrder);
    }

    //  USER ORDER HISTORY 
    public List<OrderResponseDto> getOrdersByUser(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return orderRepository.findByUser(user)
                .stream()
                .map(OrderResponseDto::from)
                .collect(Collectors.toList());
    }

    //  ADMIN: ALL ORDERS 
    public List<OrderResponseDto> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDto::from)
                .collect(Collectors.toList());
    }

    //  ADMIN: UPDATE STATUS 
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);

        return OrderResponseDto.from(updatedOrder);
    }
}
