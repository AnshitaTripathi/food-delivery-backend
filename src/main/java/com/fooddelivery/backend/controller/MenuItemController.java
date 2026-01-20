package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.MenuItemRequestDto;
import com.fooddelivery.backend.dto.MenuItemResponseDto;
import com.fooddelivery.backend.service.MenuItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // ADMIN
    @PostMapping("/api/restaurants/{restaurantId}/menu")
    public ResponseEntity<MenuItemResponseDto> addMenuItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody MenuItemRequestDto dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuItemService.addMenuItem(restaurantId, dto));
    }

    // USER + ADMIN
    @GetMapping("/api/restaurants/{restaurantId}/menu")
    public ResponseEntity<List<MenuItemResponseDto>> getMenu(
            @PathVariable Long restaurantId
    ) {
        return ResponseEntity.ok(menuItemService.getMenuByRestaurant(restaurantId));
    }
}
