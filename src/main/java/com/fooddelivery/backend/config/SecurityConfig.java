package com.fooddelivery.backend.config;

import com.fooddelivery.backend.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(auth -> auth

            // Swagger
            .requestMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**"
            ).permitAll()

            // Auth & signup
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

            //  MENU APIs 
            .requestMatchers(HttpMethod.GET, "/api/restaurants/*/menu")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

            .requestMatchers(HttpMethod.POST, "/api/restaurants/*/menu")
                .hasAuthority("ROLE_ADMIN")

            //  RESTAURANTS
            .requestMatchers(HttpMethod.GET, "/api/restaurants/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

            .requestMatchers(HttpMethod.POST, "/api/restaurants/**")
                .hasAuthority("ROLE_ADMIN")

            // CART 
            .requestMatchers("/api/cart/**")
                .hasAuthority("ROLE_USER")

            //  ORDERS 
            .requestMatchers("/api/orders/**")
                .hasAuthority("ROLE_USER")

            //  ADMIN USERS 
            .requestMatchers("/api/users/**")
                .hasAuthority("ROLE_ADMIN")

            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

}
