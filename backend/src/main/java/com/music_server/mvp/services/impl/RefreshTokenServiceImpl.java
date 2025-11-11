package com.music_server.mvp.services.impl;

import org.springframework.stereotype.Service;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.RefreshTokenEntity;
import com.music_server.mvp.repositories.PlaylistRepository;
import com.music_server.mvp.repositories.RefreshTokenRepository;
import com.music_server.mvp.repositories.UserRepository;
import com.music_server.mvp.services.PlaylistService;
import com.music_server.mvp.services.RefreshTokenService;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class RefreshTokenServiceImpl extends GenericServiceImpl<RefreshTokenEntity, Long,  RefreshTokenRepository> implements RefreshTokenService{

    @Value("${security.jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        super(repository);
        this.refreshTokenRepository = repository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public RefreshTokenEntity createRefreshToken(Long userId) {

        RefreshTokenEntity existing = refreshTokenRepository.findByUserId(userId).orElseThrow();
        String hashedRefreshToken = passwordEncoder.encode(generateRefreshToken());
        if (existing != null) {
            existing.setToken(hashedRefreshToken);
            existing.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            return refreshTokenRepository.save(existing);
        } else {
            
            RefreshTokenEntity refreshToken = new RefreshTokenEntity();
            refreshToken.setUser(userRepository.findById(userId).orElseThrow());
            refreshToken.setToken(hashedRefreshToken);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            return refreshTokenRepository.save(refreshToken);
        }
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again");
        }
        return token;
    }

    

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).orElseThrow());
    }

    @Transactional
    public RefreshTokenEntity update(RefreshTokenEntity token) {
        return refreshTokenRepository.findByToken(token.getToken()).map(foundToken -> {
            Optional.ofNullable(token.getToken()).ifPresent(foundToken::setToken);
            return repository.save(token);
        }).orElseThrow(()-> new RuntimeException("Refresh Token Not Found"));
    }

    

}
