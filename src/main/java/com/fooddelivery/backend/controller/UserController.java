package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.UserRequestDto;
import com.fooddelivery.backend.dto.UserResponseDto;
import com.fooddelivery.backend.dto.UserUpdateDto;
import com.fooddelivery.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //  CREATE USER
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto dto
    ) {
        return new ResponseEntity<>(
                userService.createUser(dto),
                HttpStatus.CREATED
        );
    }

    //  GET ALL USERS
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
public ResponseEntity<UserResponseDto> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UserUpdateDto dto
) {
    return ResponseEntity.ok(userService.updateUser(id, dto));
}

}

