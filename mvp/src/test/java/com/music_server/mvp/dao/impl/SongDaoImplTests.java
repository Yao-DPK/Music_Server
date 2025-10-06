package com.music_server.mvp.dao.impl;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import javax.swing.tree.RowMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.mvp.TestDataUtil;
import com.music_server.mvp.dao.SongDao;
import com.music_server.mvp.dao.impl.SongDaoImpl;
import com.music_server.mvp.domain.Song;

@ExtendWith(MockitoExtension.class)
public class SongDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SongDaoImpl test_song;

    @Test 
    public void testThatCreatePlaylistGeneratesCorrectSql(){
        Song song = TestDataUtil.createTestSong("Sonic CD Palmtree Panic.mp3");
        test_song.create(song);

        verify(jdbcTemplate).update(
            eq("INSERT INTO song (title) VALUES (?)"),
            eq("Sonic CD Palmtree Panic.mp3")
            );

    }

    private static void getSongforTest(){
        
    }

    @Test
    public void ReadOneSongTest(){
        test_song.findOne("Sonic CD Palmtree Panic.mp3");
        verify(jdbcTemplate).query(
            eq("SELECT id, title FROM song WHERE title = ? LIMIT 1"),
            ArgumentMatchers.<SongDaoImpl.SongRowMapper>any(),
            eq("Sonic CD Palmtree Panic.mp3")
            );
    }
}
