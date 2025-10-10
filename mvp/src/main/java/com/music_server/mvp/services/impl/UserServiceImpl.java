package com.music_server.mvp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.repositories.UserRepository;
import com.music_server.mvp.services.UserService;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

}
