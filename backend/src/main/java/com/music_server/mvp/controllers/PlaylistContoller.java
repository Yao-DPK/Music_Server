package com.music_server.mvp.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
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
    private SongService songService;


    private Mapper<PlaylistEntity, PlaylistDto> playlistMapper;

    @Autowired
    public PlaylistContoller(PlaylistService playlistService, SongService songService, Mapper<PlaylistEntity, PlaylistDto> playlistMapper){
        this.playlistService = playlistService;
        this.songService = songService;
        this.playlistMapper = playlistMapper;   
    }
    

    @PostMapping(path = "/me")
    public ResponseEntity createPlaylist(@Valid @RequestBody PlaylistDto playlistDto, Authentication connectedUser){

        PlaylistEntity playlistEntity = playlistMapper.mapFrom(playlistDto);
        playlistEntity.setCreator(connectedUser.getName());
        PlaylistEntity savedPlaylist = playlistService.create(playlistEntity);
        return new ResponseEntity<>(playlistMapper.mapTo(savedPlaylist), HttpStatus.CREATED);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<PlaylistDto>> getUsersPlaylists(Authentication connectedUser){
        List<PlaylistEntity> playlists =
            Optional.ofNullable(
                playlistService.findUsersPlaylistsByUsername(connectedUser.getName())
            ).orElse(Collections.emptyList());

        List<PlaylistDto> result = playlists.stream()
            .map(playlistMapper::mapTo)
            .collect(Collectors.toList());

        System.out.printf("Playlists Requested by user: %s%n", result);

        return ResponseEntity.ok(result);
}

    @GetMapping(path = "/me/{id}")
        public ResponseEntity<PlaylistDto> getUsersPlaylistById(Authentication connectedUser, @PathVariable("id") Long id){
            Optional<PlaylistEntity> playlist = this.playlistService.findById(id);

            return playlist.map(playlistEntity ->{
                PlaylistDto playlistDto = playlistMapper.mapTo(playlistEntity);
                return new ResponseEntity<>(playlistDto, HttpStatus.OK);
    
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            
    }

    @PostMapping(path = "/me/songs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<PlaylistDto> AddSongToPlaylist(Authentication connectedUser, @RequestPart("title") String title){
            System.out.println("Add new Song");
            SongEntity songEntity = new SongEntity();
            songEntity.setTitle(title);
            songEntity.setOwner(connectedUser.getName());

            SongEntity savedSong = songService.create(songEntity);

            PlaylistEntity defaultPlaylist = this.playlistService.findUserPlaylistByTitle(connectedUser.getName(), "All Songs").orElse(null);
            PlaylistEntity playlist = this.playlistService.addSong(defaultPlaylist.getId(), savedSong.getId());
            if(playlist == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            System.out.printf("Sent Playlist: ", playlistMapper.mapTo(playlist));
            return ResponseEntity.ok(playlistMapper.mapTo(playlist));        
    }

}
