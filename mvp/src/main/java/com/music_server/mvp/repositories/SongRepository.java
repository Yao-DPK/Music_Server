package com.music_server.mvp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music_server.mvp.domain.entities.SongEntity;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long>{
    List<SongEntity> findAllByOwner_Id(Long ownerId);
    List<SongEntity> findAllByOwner_Username(String username);
}
