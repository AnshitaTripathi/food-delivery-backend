package com.fooddelivery.backend.config;

import com.fooddelivery.backend.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
            // Stateless JWT security
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                //  SWAGGER
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                ).permitAll()

                //  AUTH & SIGNUP 
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                //  PUBLIC RESTAURANT APIs
                .requestMatchers(HttpMethod.GET, "/api/restaurants/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/restaurants/*/menu").permitAll()

                // USER APIs
                .requestMatchers("/api/cart/**").hasRole("USER")
                .requestMatchers("/api/users/me").hasRole("USER")
                .requestMatchers("/api/users/me/**").hasRole("USER")

                //  ADMIN APIs 
                .requestMatchers(HttpMethod.POST, "/api/restaurants/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/restaurants/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/restaurants/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/restaurants/*/menu").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/restaurants/*/menu/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/restaurants/*/menu/**").hasRole("ADMIN")

                .requestMatchers("/api/users/**").hasRole("ADMIN")

                //  FALLBACK 
                .anyRequest().authenticated()
            )

            // JWT Filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
