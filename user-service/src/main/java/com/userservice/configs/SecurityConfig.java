package com.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//TODO setup authorization
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll() // Allow every request without authentication
                )
                // 2. Disable CSRF protection (common for stateless APIs, adjust if needed)
                .csrf(AbstractHttpConfigurer::disable)
                // 3. Optional: Disable HTTP Basic and Form Login if they interfere
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
