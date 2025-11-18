package com.music_server.mvp.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.music_server.mvp.domain.dto.PlaylistDto;
import com.music_server.mvp.domain.dto.SongDto;
import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.SongEntity;
import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.mappers.Mapper;
import com.music_server.mvp.security.MusicUserDetails;
import com.music_server.mvp.services.SongService;
import com.music_server.mvp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/songs")
public class SongController {
    
    private SongService songService;

    private Mapper<UserEntity, UserDto> userMapper;

    private Mapper<SongEntity, SongDto> songMapper;

    private Mapper<PlaylistEntity, PlaylistDto> playlistMapper;

    @Autowired
    public SongController(SongService songService, Mapper<SongEntity, SongDto> songMapper){
        this.songService = songService;
        this.songMapper = songMapper;
    }

    @PostMapping(
        path = "/me",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<SongDto> uploadSong(
            @RequestPart("file") MultipartFile file,
            @RequestPart("title") String title,
            Authentication connectedUser
    ) {
        try {
            SongEntity songEntity = new SongEntity();
            songEntity.setTitle(title);
            songEntity.setOwner(connectedUser.getName());

            SongEntity savedSong = songService.create(songEntity);

            System.out.println("Receiving Song: " + title);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(songMapper.mapTo(savedSong));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<SongDto>> getUsersSongs(/* @AuthenticationPrincipal MusicUserDetails userDetails,  */
    Authentication connectedUser)
    {   
        System.out.println("Songs Requested");
        List<SongEntity> songs = songService.findUsersSongsByUsername(connectedUser.getName());
        return new ResponseEntity<>(songs.stream().map(songMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }
}
