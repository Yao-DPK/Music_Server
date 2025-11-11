package com.music_server.mvp.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.music_server.mvp.domain.entities.UserEntity;

public interface AuthService {

    UserDetails authenticate(String username, String password);
    String generateAccessToken(String username);
    UserDetails validateToken(String token);
    void register(String username, String password);
}
