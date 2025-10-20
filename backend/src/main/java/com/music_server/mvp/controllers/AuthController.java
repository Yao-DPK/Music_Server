package com.music_server.mvp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.music_server.mvp.domain.dto.AuthResponse;
import com.music_server.mvp.domain.dto.LoginRequest;
import com.music_server.mvp.domain.dto.RegisterRequest;
import com.music_server.mvp.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        UserDetails userDetails = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String tokenValue = authService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(tokenValue, 86400);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody LoginRequest registerRequest){
        authService.register(registerRequest.getUsername(), registerRequest.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
