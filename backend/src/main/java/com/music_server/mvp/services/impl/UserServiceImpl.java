/* package com.music_server.mvp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.repositories.UserRepository;
import com.music_server.mvp.services.UserService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends GenericServiceImpl<UserEntity, Long, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
            super(repository);
        }
    
    @Transactional
    public UserEntity fullUpdate(Long id, UserEntity userEntity){

        return repository.findById(id).map(foundUser -> {
            foundUser.setUsername(userEntity.getUsername());
            foundUser.setPassword(userEntity.getPassword());
            return repository.save(foundUser);
        }).orElseThrow(() -> new RuntimeException("User not Found"));
    }

    @Transactional
    public UserEntity partialUpdate(Long id, UserEntity userEntity){
        return repository.findById(id).map(foundUser -> {
            Optional.ofNullable(userEntity.getUsername()).ifPresent(foundUser::setUsername);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(foundUser::setPassword);
            return repository.save(foundUser);
        }).orElseThrow(() -> new RuntimeException("User not Found")); 
    }
    
    public boolean existsByUsername(String username){
        return repository.existsByUsername(username);
    }
}
 */