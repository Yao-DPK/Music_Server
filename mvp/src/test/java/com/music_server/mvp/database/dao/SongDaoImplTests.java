package com.music_server.mvp.database.dao;
import com.music_server.database.config.dao.SongDao;
import com.music_server.database.config.dao.impl.SongDaoImpl;
import com.music_server.database.config.domain.Song;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
public class SongDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SongDaoImpl test_song;

    @Test 
    public void testThatCreatePlaylistGeneratesCorrectSql(){
        Song song = new Song("Sonic CD Palmtree Panic.mp3");
        test_song.create(song);

        verify(jdbcTemplate).update(
            eq("INSERT INTO song (title) VALUES (?)"),
            eq("Sonic CD Palmtree Panic.mp3")
            );

    }
}
