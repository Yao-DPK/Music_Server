package com.music_server.mvp.domain.dto;

import java.util.List;

public class PlaylistDto {
    private Long id;

    private String title;

    private UserDto creator;

    private List<PlaylistItemDto> items;

    


    public PlaylistDto() {
    }

    

    public PlaylistDto(Long id, String title, UserDto creator, List<PlaylistItemDto> items) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.items = items;
    }


    public PlaylistDto(String title, UserDto creator) {
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

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }

    public List<PlaylistItemDto> getItems() {
        return items;
    }

    public void setItems(List<PlaylistItemDto> items) {
        this.items = items;
    }

    
}
