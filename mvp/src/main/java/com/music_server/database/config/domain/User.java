package com.music_server.database.config.domain;
import jakarta.persistence.*;
import lombok.*;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }


    public User(){
        this.username = "";
        this.password = "";
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}