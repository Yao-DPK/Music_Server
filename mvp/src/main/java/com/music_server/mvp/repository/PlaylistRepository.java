package com.music_server.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music_server.mvp.domain.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
    
}
