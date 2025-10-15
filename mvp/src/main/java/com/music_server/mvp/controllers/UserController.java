package com.music_server.mvp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.mappers.Mapper;
import com.music_server.mvp.services.UserService;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper<UserEntity, UserDto> userMapper;

    @PostMapping(path = "/")
    public ResponseEntity<UserDto> create(@RequestBody  UserDto user){
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity savedUserEntity = userService.create(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    } 

    @GetMapping(path = "/")
    public ResponseEntity<List<UserDto>> findAll(){
        List<UserEntity> users = userService.findAll();
        return new ResponseEntity<>(users.stream().map(userMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
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
/* 
    @PostMapping(path= "/{id}/songs")
    public ResponseEntity uploadSong(@PathVariable("id") Long id, @RequestBody SongDto songDto){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/songs")
    public ResponseEntity<List<SongDto>> getUsersSongs(@PathVariable("id") Long id){
        List<SongDto> songs = new ArrayList<>();
        songs.add(new SongDto("Sonic.mp3", id));
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    
    @PostMapping(path = "/{id}/playlists")
    public ResponseEntity createPlaylist(@PathVariable("id") Long id, @RequestBody PlaylistDto playlistDto){
        PlaylistDto playlistDto2 = playlistDto;
        return new ResponseEntity<>(playlistDto2, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/playlists")
    public ResponseEntity<List<PlaylistDto>> getUsersPlaylists(@PathVariable("id") Long id){
        List<PlaylistDto> playlists = new ArrayList<>();
        playlists.add(new PlaylistDto("Sport", id));
        playlists.add(new PlaylistDto("Chill", id));

        return new ResponseEntity<>(playlists, HttpStatus.OK);
    } */
    
}