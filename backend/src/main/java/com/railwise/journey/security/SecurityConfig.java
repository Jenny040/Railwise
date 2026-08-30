package com.railwise.journey.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Hackathon-speed security config: CSRF disabled (stateless JSON API),
 * API endpoints open so the frontend team can integrate immediately.
 *
 * Before any real deployment: add JWT/OAuth2 auth here and lock down
 * write endpoints (POST/PATCH/DELETE) to authenticated commuters/admins.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/**", "/actuator/health").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
