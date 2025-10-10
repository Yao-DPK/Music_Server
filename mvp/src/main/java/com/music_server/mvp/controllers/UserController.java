package com.music_server.mvp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.mappers.Mapper;
import com.music_server.mvp.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper<UserEntity, UserDto> userMapper;

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody  UserDto user){
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity savedUserEntity = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    } 

    /* @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getUsers(){
        
    }
 */

}
