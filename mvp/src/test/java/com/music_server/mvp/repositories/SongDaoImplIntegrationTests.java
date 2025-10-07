/* package com.music_server.mvp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.dao.SongDao;
import com.music_server.mvp.dao.impl.SongDaoImpl;
import com.music_server.mvp.domain.Song;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

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

    @Test
    public void MultipleSongCreationandReadTest(){
        Song song1 = TestDataUtil.createTestSong("Sonic.mp3");
        test_song.create(song1);
        Song song2 = TestDataUtil.createTestSong("Mario.mp3");
        test_song.create(song2);


        List<Song> results = test_song.findAll();

        assertThat(results).extracting(Song::getTitle).containsExactly("Sonic.mp3", "Mario.mp3");
    }

    
}   
 */