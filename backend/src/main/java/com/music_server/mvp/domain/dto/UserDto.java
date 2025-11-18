package com.music_server.mvp.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;
    
    

    public UserDto() {
    }


    public UserDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
