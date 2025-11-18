package com.music_server.mvp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.music_server.mvp.services.PlaylistService;
import com.music_server.mvp.services.SongService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/playlists")
public class PlaylistContoller {
    
    private PlaylistService playlistService;

    private Mapper<PlaylistEntity, PlaylistDto> playlistMapper;

    @Autowired
    public PlaylistContoller(PlaylistService playlistService, Mapper<PlaylistEntity, PlaylistDto> playlistMapper){
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;   
    }
    

    @PostMapping(path = "/me")
    public ResponseEntity createPlaylist(@AuthenticationPrincipal MusicUserDetails userDetails, @Valid @RequestBody PlaylistDto playlistDto, Authentication connectedUser){

        PlaylistEntity playlistEntity = playlistMapper.mapFrom(playlistDto);
        playlistEntity.setCreator(connectedUser.getName());
        PlaylistEntity savedPlaylist = playlistService.create(playlistEntity);
        return new ResponseEntity<>(playlistMapper.mapTo(savedPlaylist), HttpStatus.CREATED);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<PlaylistDto>> getUsersPlaylists(@AuthenticationPrincipal MusicUserDetails userDetails, Authentication connectedUser){
        List<PlaylistEntity> playlists = playlistService.findUsersPlaylistsByUsername(connectedUser.getName());
        return new ResponseEntity<>(playlists.stream().map(playlistMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }
}

