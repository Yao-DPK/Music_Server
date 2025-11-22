package com.music_server.mvp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.SongEntity;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long>{

    List<PlaylistEntity> findAllByCreator(String creator);

    Optional<PlaylistEntity> findById(Long id);
    Optional<PlaylistEntity> findByCreatorAndTitle(String creator, String title);
}
