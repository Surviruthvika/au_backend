package com.anurag.examhall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {}) // enable CORS
            .csrf(csrf -> csrf.disable()) // disable CSRF
            .authorizeHttpRequests(auth -> auth

                // ✅ VERY IMPORTANT (fix preflight error)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // allow all APIs
                .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // ✅ allow your frontend (Vercel)
        config.setAllowedOriginPatterns(List.of(
            "https://project-tt-frontend-z674.vercel.app"
        ));

        // ✅ allow all HTTP methods
        config.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // ✅ allow all headers
        config.setAllowedHeaders(List.of("*"));

        // ✅ allow credentials (JWT, cookies)
        config.setAllowCredentials(true);

        // cache preflight response
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}