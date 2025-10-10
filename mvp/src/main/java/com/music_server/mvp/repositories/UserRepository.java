package com.music_server.mvp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music_server.mvp.domain.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    
}
