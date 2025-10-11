package com.music_server.mvp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.repositories.UserRepository;
import com.music_server.mvp.services.UserService;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity create(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public boolean existById(Long id){
        return userRepository.existsById(id);
    }

    @Override
    public UserEntity fullUpdate(Long id, UserEntity userEntity){

        return userRepository.findById(id).map(foundUser -> {
            foundUser.setUsername(userEntity.getUsername());
            foundUser.setPassword(userEntity.getPassword());
            return foundUser;
        }).orElseThrow(() -> new RuntimeException("User not Found"));
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity userEntity){
        return userRepository.findById(id).map(foundUser -> {
            Optional.ofNullable(userEntity.getUsername()).ifPresent(foundUser::setUsername);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(foundUser::setPassword);
            return userRepository.save(foundUser);
        }).orElseThrow(() -> new RuntimeException("User not Found")); 
    }

    @Override
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
