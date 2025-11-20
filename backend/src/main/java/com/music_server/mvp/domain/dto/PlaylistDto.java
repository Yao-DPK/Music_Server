package com.music_server.mvp.domain.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class PlaylistDto {
    private Long id;

    @NotBlank(message = " Playlist title is required")
    private String title;

    private String creator;

    private List<PlaylistItemDto> songs;


    public PlaylistDto() {
    }

    public PlaylistDto(Long id, String title, String creator, List<PlaylistItemDto> songs) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.songs = songs;
    }


    public PlaylistDto(String title, String creator) {
        this.title = title;
        this.creator = creator;
    }

    public PlaylistDto(String title) {
        this.title = title;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<PlaylistItemDto> getItems() {
        return songs;
    }

    public void setItems(List<PlaylistItemDto> items) {
        this.songs = items;
    }

    
}
