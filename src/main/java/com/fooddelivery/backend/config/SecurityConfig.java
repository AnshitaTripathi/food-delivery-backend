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
            // JWT = Stateless
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

                

                // Auth APIs
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                // RESTAURANTS
             .requestMatchers(HttpMethod.POST, "/api/restaurants/**")
            .hasAuthority("ROLE_ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/restaurants/**")
            .authenticated()

                // USER-only APIs (MUST COME FIRST)
                .requestMatchers("/api/users/me").hasAuthority("ROLE_USER")
                .requestMatchers("/api/users/me/**").hasAuthority("ROLE_USER")

                // ADMIN-only APIs
                .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")

                // Everything else
                .anyRequest().authenticated()
            )

            // JWT Filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
   