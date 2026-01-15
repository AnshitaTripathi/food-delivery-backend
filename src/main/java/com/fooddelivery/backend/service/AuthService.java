package com.fooddelivery.backend.service;

import com.fooddelivery.backend.config.JwtUtil;
import com.fooddelivery.backend.dto.LoginRequestDto;
import com.fooddelivery.backend.dto.LoginResponseDto;
import com.fooddelivery.backend.entity.User;
import com.fooddelivery.backend.exceptions.ResourceNotFoundException;
import com.fooddelivery.backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

   public LoginResponseDto login(LoginRequestDto dto) {

    User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        throw new ResourceNotFoundException("Invalid email or password");
    }

    String token = jwtUtil.generateToken(user.getEmail());

    
    return new LoginResponseDto(token, user.getEmail());
}

}
