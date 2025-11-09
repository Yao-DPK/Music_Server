package com.music_server.mvp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.music_server.mvp.security.MusicUserDetails;
import com.music_server.mvp.services.AuthService;
import com.music_server.mvp.services.RefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService){
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        MusicUserDetails userDetails = (MusicUserDetails) authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        
        String jwt = authService.generateAccessToken(userDetails);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return new ResponseEntity<>(new JwtResponse(jwt, refreshToken.getToken()), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody LoginRequest registerRequest){
        authService.register(registerRequest.getUsername(), registerRequest.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    MusicUserDetails userDetails = (MusicUserDetails) authService.authenticate(user.getUsername(), user.getPassword());
                    String token = authService.generateAccessToken(userDetails);
                    return ResponseEntity.ok(new JwtResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }
}
