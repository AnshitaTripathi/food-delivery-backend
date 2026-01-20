package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.MenuItemRequestDto;
import com.fooddelivery.backend.dto.MenuItemResponseDto;
import com.fooddelivery.backend.entity.MenuItem;
import com.fooddelivery.backend.entity.Restaurant;
import com.fooddelivery.backend.exceptions.ResourceNotFoundException;
import com.fooddelivery.backend.repository.MenuItemRepository;
import com.fooddelivery.backend.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemRepository menuItemRepository,
                           RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // ADMIN
    public MenuItemResponseDto addMenuItem(Long restaurantId, MenuItemRequestDto dto) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setPrice(dto.getPrice());
        menuItem.setRestaurant(restaurant);

        MenuItem saved = menuItemRepository.save(menuItem);

        return mapToDto(saved);
    }

    // USER + ADMIN
    public List<MenuItemResponseDto> getMenuByRestaurant(Long restaurantId) {

        return menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private MenuItemResponseDto mapToDto(MenuItem item) {
        return new MenuItemResponseDto(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.isAvailable()
        );
    }
}
