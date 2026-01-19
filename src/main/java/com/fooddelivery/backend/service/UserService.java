package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.UserRequestDto;
import com.fooddelivery.backend.dto.UserResponseDto;
import com.fooddelivery.backend.dto.UserSelfUpdateDto;
import com.fooddelivery.backend.dto.UserUpdateDto;
import com.fooddelivery.backend.entity.Role;
import com.fooddelivery.backend.entity.User;
import com.fooddelivery.backend.exceptions.DuplicateResourceException;
import com.fooddelivery.backend.exceptions.ResourceNotFoundException;
import com.fooddelivery.backend.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== CREATE USER =====
    public UserResponseDto createUser(UserRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    // ===== GET ALL USERS =====
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    // ===== GET USER BY ID =====
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // ===== UPDATE USER =====
    public UserResponseDto updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User updatedUser = userRepository.save(user);

        return new UserResponseDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail()
        );
    }

    // ===== UPDATE CURRENT LOGGED-IN USER =====
public UserResponseDto updateCurrentUser(UserSelfUpdateDto dto) {

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String emailFromToken = authentication.getName();

    User user = userRepository.findByEmail(emailFromToken)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    // Check email uniqueness (if changed)
    if (!user.getEmail().equals(dto.getEmail())
            && userRepository.existsByEmail(dto.getEmail())) {
        throw new DuplicateResourceException("Email already exists");
    }

    user.setName(dto.getName());
    user.setEmail(dto.getEmail());

    User updatedUser = userRepository.save(user);

    return new UserResponseDto(
            updatedUser.getId(),
            updatedUser.getName(),
            updatedUser.getEmail()
    );
}

// ===== GET CURRENT LOGGED-IN USER =====
public UserResponseDto getCurrentUser() {

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getEmail()
    );
}



}
