package com.music_server.mvp.controllers;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.music_server.mvp.domain.dto.AuthResponse;
import com.music_server.mvp.domain.dto.JwtResponse;
import com.music_server.mvp.domain.dto.LoginRequest;
import com.music_server.mvp.domain.dto.RegisterRequest;
import com.music_server.mvp.domain.dto.TokenRefreshRequest;
import com.music_server.mvp.domain.entities.RefreshTokenEntity;
import com.music_server.mvp.repositories.RefreshTokenRepository;
import com.music_server.mvp.security.MusicUserDetails;
import com.music_server.mvp.services.AuthService;
import com.music_server.mvp.services.RefreshTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder){
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response){
        MusicUserDetails userDetails = (MusicUserDetails) authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        
        String jwt = authService.generateAccessToken(userDetails.getUsername());
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        // Send cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(7))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new ResponseEntity<>(new AuthResponse(jwt, 86400L), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody LoginRequest registerRequest){
        authService.register(registerRequest.getUsername(), registerRequest.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Refreshing Access Token");
        // Get refresh token from cookie
        String refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(c -> "refresh_token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("No refresh token cookie"));

        // Find and verify in DB
        RefreshTokenEntity tokenEntity = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (tokenEntity.isExpired()) {
            refreshTokenService.deleteByUserId(tokenEntity.getUser().getId());
            throw new RuntimeException("Refresh token expired");
        }

        // Rotate token
        String newRefreshToken = refreshTokenService.generateRefreshToken();
        tokenEntity.setToken(passwordEncoder.encode(newRefreshToken));
        tokenEntity.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshTokenService.update(tokenEntity);


        // Issue new access token
        String newAccessToken = authService.generateAccessToken(tokenEntity.getUser().getUsername());

        // Send new cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(7))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        
        return new ResponseEntity<>(new AuthResponse(newAccessToken, 86400L), HttpStatus.OK);
    }

}
