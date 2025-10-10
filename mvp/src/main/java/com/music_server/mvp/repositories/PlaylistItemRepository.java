package com.music_server.mvp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music_server.mvp.domain.entities.PlaylistItemEntity;

@Repository
public interface PlaylistItemRepository extends JpaRepository<PlaylistItemEntity, Long> {
    
}
