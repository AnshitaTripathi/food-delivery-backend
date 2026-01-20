package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.RestaurantRequestDto;
import com.fooddelivery.backend.dto.RestaurantResponseDto;
import com.fooddelivery.backend.entity.Restaurant;
import com.fooddelivery.backend.exceptions.ResourceNotFoundException;
import com.fooddelivery.backend.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // ADMIN
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto dto) {

        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());

        Restaurant saved = restaurantRepository.save(restaurant);

        return mapToDto(saved);
    }

    // USER + ADMIN
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // USER + ADMIN
    public RestaurantResponseDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        return mapToDto(restaurant);
    }

    private RestaurantResponseDto mapToDto(Restaurant restaurant) {
        return new RestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.isOpen()
        );
    }
}

