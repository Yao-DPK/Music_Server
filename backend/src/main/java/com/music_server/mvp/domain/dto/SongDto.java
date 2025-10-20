package com.music_server.mvp.domain.dto;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class SongDto {
    private Long id;

    @NotBlank(message = "Song title is required")
    private String title;

    private UserDto owner;

    public SongDto() {
    }

    public SongDto(String title, UserDto owner) {
        this.title = title;
        this.owner = owner;
    }


    public SongDto(String title) {
        this.title = title;
    }

    public SongDto(Long id, String title, UserDto owner) {
        this.id = id;
        this.title = title;
        this. owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this. owner = owner;
    }

    
}
