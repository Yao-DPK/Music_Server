package com.music_server.mvp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.SongEntity;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long>{


    List<PlaylistEntity> findAllByCreator_Id(Long userId);
    List<PlaylistEntity> findAllByCreator_Username(String username);

}
