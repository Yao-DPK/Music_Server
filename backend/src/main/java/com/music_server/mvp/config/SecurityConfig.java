package com.music_server.mvp.config;

import java.net.Authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.exceptions.CustomAccessDeniedHandler;
import com.music_server.mvp.exceptions.JwtAuthenticationEntryPoint;
//import com.music_server.mvp.repositories.UserRepository;
//import com.music_server.mvp.security.JwtAuthenticationFilter;
import com.music_server.mvp.services.AuthService;
//import com.music_server.mvp.services.MusicUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
import com.music_server.mvp.security.CustomCorsConfiguration;

//This is just a Basic implementation, More needs to be done for a production ready environment

@Configuration
public class SecurityConfig {

    private CustomCorsConfiguration customCorsConfiguration;

    @Autowired
    public SecurityConfig(
        CustomCorsConfiguration customCorsConfiguration) {
        this.customCorsConfiguration = customCorsConfiguration;
    }

/*     @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthService authService){
        return new JwtAuthenticationFilter(authService);
    }
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http/* , JwtAuthenticationFilter jwtAuthenticationFilter */) throws Exception{
        
        http
                
                .csrf(csrf -> csrf.disable())
                .cors(c -> c.configurationSource(customCorsConfiguration))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                    /*.requestMatchers(HttpMethod.GET, "/api/v1/playlists/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/songs/**").permitAll() */
                    .anyRequest().authenticated()
                )
                .oauth2ResourceServer(auth -> 
                auth.jwt(token -> token.jwtAuthenticationConverter(new KeycloackJwtAuthenticationConverter())));
                
                /* .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 401 handler
                .accessDeniedHandler(customAccessDeniedHandler)         // 403 handler
                ) 
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);*/
        return http.build();
    }

    /* @Bean 
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    } */
}
