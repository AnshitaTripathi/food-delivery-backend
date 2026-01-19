package com.fooddelivery.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Disable default in-memory users
        return username -> {
            throw new UnsupportedOperationException(
                "UserDetailService is not used. JWT only."
            );
        };
    }
}

