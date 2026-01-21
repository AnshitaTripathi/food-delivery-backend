package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.CartItemRequestDto;
import com.fooddelivery.backend.dto.CartResponseDto;
import com.fooddelivery.backend.entity.Cart;
import com.fooddelivery.backend.entity.CartItem;
import com.fooddelivery.backend.entity.MenuItem;
import com.fooddelivery.backend.entity.User;
import com.fooddelivery.backend.exceptions.ResourceNotFoundException;
import com.fooddelivery.backend.repository.CartRepository;
import com.fooddelivery.backend.repository.MenuItemRepository;
import com.fooddelivery.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    public CartService(
            CartRepository cartRepository,
            UserRepository userRepository,
            MenuItemRepository menuItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    //  ADD ITEM TO CART 
    public CartResponseDto addItemToCart(String userEmail, CartItemRequestDto dto) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu item not found"));

        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(i -> i.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(
                    existingItem.get().getQuantity() + dto.getQuantity()
            );
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMenuItem(menuItem);
            cartItem.setQuantity(dto.getQuantity());
            cart.getItems().add(cartItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return CartResponseDto.from(savedCart);
    }

    //  VIEW CART 
    public CartResponseDto getCartByUser(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart is empty"));

        return CartResponseDto.from(cart);
    }

    //  REMOVE ITEM 
    public void removeItemFromCart(String userEmail, Long menuItemId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cart.getItems().removeIf(
                item -> item.getMenuItem().getId().equals(menuItemId)
        );

        cartRepository.save(cart);
    }

    //  CLEAR CART 
    public void clearCart(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}