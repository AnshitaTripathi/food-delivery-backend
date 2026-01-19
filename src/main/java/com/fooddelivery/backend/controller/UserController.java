package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.UserRequestDto;
import com.fooddelivery.backend.dto.UserResponseDto;
import com.fooddelivery.backend.dto.UserSelfUpdateDto;
import com.fooddelivery.backend.dto.UserUpdateDto;
import com.fooddelivery.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User APIs", description = "APIs related to user management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //  CREATE USER 
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with name, email and encrypted password"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto dto
    ) {
        UserResponseDto response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //  GET ALL USERS 
    @Operation(
            summary = "Get all users",
            description = "Fetches list of all registered users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //  GET USER BY ID 
    @Operation(
            summary = "Get user by ID",
            description = "Fetch a user using user ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // UPDATE USER 
    @Operation(
            summary = "Update user details",
            description = "Updates user name and email using user ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto dto
    ) {
        UserResponseDto updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

        // ===== GET CURRENT LOGGED-IN USER =====
    @Operation(
            summary = "Get current logged-in user",
            description = "Fetches the profile of the authenticated user using JWT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile() {
        UserResponseDto user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
    // ===== UPDATE CURRENT LOGGED-IN USER =====
@Operation(
        summary = "Update current logged-in user",
        description = "Updates profile details of the authenticated user"
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "409", description = "Email already exists")
})
@SecurityRequirement(name = "bearerAuth")
@PutMapping("/me")
public ResponseEntity<UserResponseDto> updateMyProfile(
        @Valid @RequestBody UserSelfUpdateDto dto
) {
    return ResponseEntity.ok(userService.updateCurrentUser(dto));
}


}
