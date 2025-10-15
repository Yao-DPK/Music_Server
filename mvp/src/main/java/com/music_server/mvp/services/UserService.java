package com.music_server.mvp.services;

import java.util.List;
import java.util.Optional;

import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.UserEntity;

public interface UserService extends GenericService<UserEntity, Long> {

    UserEntity fullUpdate(Long id, UserEntity entity);

    UserEntity partialUpdate(Long id, UserEntity entity);

    boolean existsByUsername(String username);
    
}
