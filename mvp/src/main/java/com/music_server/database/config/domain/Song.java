package com.music_server.database.config.domain;
import jakarta.persistence.*;;

public class Song {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String Titre;

}   