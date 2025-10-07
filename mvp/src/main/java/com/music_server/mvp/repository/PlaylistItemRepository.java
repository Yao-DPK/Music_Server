package com.music_server.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music_server.mvp.domain.PlaylistItem;

@Repository
public interface PlaylistItemRepository extends JpaRepository<PlaylistItem, Long> {
    
}
