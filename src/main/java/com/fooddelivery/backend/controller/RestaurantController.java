package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.RestaurantRequestDto;
import com.fooddelivery.backend.dto.RestaurantResponseDto;
import com.fooddelivery.backend.service.RestaurantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@SecurityRequirement(name = "bearerAuth")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // ADMIN
    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(
            @Valid @RequestBody RestaurantRequestDto dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurantService.createRestaurant(dto));
    }

    // USER + ADMIN
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    // USER + ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }
}

