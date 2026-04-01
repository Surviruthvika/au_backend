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
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // enable CORS with config source
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

        // ✅ allow your frontend (Vercel) + localhost for testing
        config.setAllowedOriginPatterns(List.of(
            //"https://project-tt-frontend.vercel.app",
            "https://project-tt-frontend-z674.vercel.app",
            "https://*.vercel.app",
            "http://localhost:3000",
            "http://localhost:*",
            "http://127.0.0.1:*"
        ));

        // ✅ allow all HTTP methods
        config.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));

        // ✅ allow all headers
        config.setAllowedHeaders(List.of("*"));

        // ✅ expose Authorization header for JWT
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        // ✅ allow credentials (JWT, cookies)
        config.setAllowCredentials(true);

        // cache preflight response for 1 hour
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
// FINAL CORS FIX