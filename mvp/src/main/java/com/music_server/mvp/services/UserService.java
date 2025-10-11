package com.music_server.mvp.services;

import java.util.List;
import java.util.Optional;

import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.UserEntity;

public interface UserService {
    UserEntity create(UserEntity user);

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    boolean existById(Long id);

    UserEntity fullUpdate(Long id, UserEntity userEntity);

    UserEntity partialUpdate(Long id, UserEntity userEntity);
    
    void delete(Long id);
    
}
