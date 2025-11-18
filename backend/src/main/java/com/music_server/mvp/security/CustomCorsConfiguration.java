package com.music_server.mvp.security;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.cors.CorsConfiguration;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomCorsConfiguration implements CorsConfigurationSource {

    @Value("${test.frontend.url}")
    private String frontend_url;

    @Value("${keycloak.auth-server-url}")
    private String keycloak_url;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();

        System.out.println(frontend_url);
        System.out.println(keycloak_url);
        config.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200", frontend_url, keycloak_url));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Set-Cookie");
        return config;
    }
}