/* package com.music_server.mvp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.music_server.mvp.domain.dto.PlaylistDto;
import com.music_server.mvp.domain.dto.SongDto;
import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.mappers.Mapper;
import com.music_server.mvp.security.MusicUserDetails;
import com.music_server.mvp.services.UserService;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    
    private UserService userService;

    private Mapper<UserEntity, UserDto> userMapper;

    private Mapper<SongEntity, SongDto> songMapper;

    private Mapper<PlaylistEntity, PlaylistDto> playlistMapper;

    @Autowired
    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper, Mapper<SongEntity, SongDto> songMapper, Mapper<PlaylistEntity, PlaylistDto> playlistMapper){
        this.userService = userService;
        this.userMapper = userMapper;
        this.songMapper = songMapper;
        this.playlistMapper = playlistMapper;   
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(Pageable pageable){
        Page<UserEntity> users = userService.findAll(pageable);
        return new ResponseEntity<>(users.stream().map(userMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal MusicUserDetails userDetails){
        UserEntity userEntity = userDetails.getUserEntity();
        UserDto userDto = userMapper.mapTo(userEntity);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id){
        Optional<UserEntity> user = userService.findById(id);
        return user.map(userEntity ->{
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> fullUpdate(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        boolean user_exists = userService.existById(id);
        
        if(!user_exists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.fullUpdate(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UserDto> partialUpdate(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        boolean user_exists = userService.existById(id);
        
        if(!user_exists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity userThatUpdates = userMapper.mapFrom(userDto);

        UserEntity updatedUserEntity = userService.partialUpdate(id, userThatUpdates);
        return new ResponseEntity<>(userMapper.mapTo(updatedUserEntity), HttpStatus.OK);

    }

    @DeleteMapping(path = "/{id}")
    public  ResponseEntity delete(@PathVariable("id") Long id){
        boolean user_exists = userService.existById(id);

        if(!user_exists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
} */