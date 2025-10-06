package com.music_server.mvp.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.dao.SongDao;
import com.music_server.mvp.dao.impl.SongDaoImpl;
import com.music_server.mvp.domain.Song;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SongDaoImplIntegrationTests {
    
    private SongDaoImpl test_song;
    
    @Autowired
    public SongDaoImplIntegrationTests(SongDaoImpl test_song){
        this.test_song = test_song;
    }

    @Test
    public void TestthatSongcanbeCreatedandRecalled(){
        Song song = TestDataUtil.createTestSong("Sonic.mp3");
        
        test_song.create(song);
        Optional<Song> result = test_song.findOne(song.getTitle());


        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo(song.getTitle());
    }

    
}   
