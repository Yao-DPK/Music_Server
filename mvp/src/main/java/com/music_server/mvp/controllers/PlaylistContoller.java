package com.music_server.mvp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createPlaylist(@AuthenticationPrincipal MusicUserDetails userDetails, @RequestBody PlaylistDto playlistDto){

        PlaylistEntity playlistEntity = playlistMapper.mapFrom(playlistDto);
        UserEntity creatorReference = new UserEntity();
        creatorReference.setId(userDetails.getId()); // juste l'id
        playlistEntity.setCreator(creatorReference);
        PlaylistEntity savedPlaylist = playlistService.create(playlistEntity);
        return new ResponseEntity<>(playlistMapper.mapTo(savedPlaylist), HttpStatus.CREATED);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<PlaylistDto>> getUsersPlaylists(@AuthenticationPrincipal MusicUserDetails userDetails){
        List<PlaylistEntity> playlists = playlistService.findUsersPlaylistsById(userDetails.getId());
        return new ResponseEntity<>(playlists.stream().map(playlistMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }
}

