package com.music_server.mvp.services;

import java.util.Optional;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.RefreshTokenEntity;

public interface RefreshTokenService extends GenericService<RefreshTokenEntity, Long>{

    Optional<RefreshTokenEntity> findByToken(String token);
    RefreshTokenEntity createRefreshToken(Long userId);
    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);
    RefreshTokenEntity update(RefreshTokenEntity token);
    int deleteByUserId(Long userId);
    String generateRefreshToken();
}
