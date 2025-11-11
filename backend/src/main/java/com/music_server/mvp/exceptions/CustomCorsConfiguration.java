package com.music_server.mvp.exceptions;

import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomCorsConfiguration implements CorsConfigurationSource {

    @Value("${test.frontend.url}")
    private String frontend_url;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();

        System.out.println(frontend_url);
        config.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200", frontend_url));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Set-Cookie");
        return config;
    }
}