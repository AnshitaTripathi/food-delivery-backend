package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.UserRequestDto;
import com.fooddelivery.backend.dto.UserResponseDto;
import com.fooddelivery.backend.dto.UserUpdateDto;
import com.fooddelivery.backend.entity.User;
import com.fooddelivery.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        User savedUser = userRepository.save(user);

        // Convert Entity → Response DTO
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

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

public UserResponseDto updateUser(Long id, UserUpdateDto dto) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!user.getEmail().equals(dto.getEmail())
            && userRepository.existsByEmail(dto.getEmail())) {
        throw new RuntimeException("Email already exists");
    }

    user.setName(dto.getName());
    user.setEmail(dto.getEmail());

    User savedUser = userRepository.save(user);

    return new UserResponseDto(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail()
    );
}

}

