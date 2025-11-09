package com.music_server.mvp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.RefreshTokenEntity;
import com.music_server.mvp.domain.entities.UserEntity;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUserId(Long userId);
    int deleteByUser(UserEntity orElseThrow);

}
