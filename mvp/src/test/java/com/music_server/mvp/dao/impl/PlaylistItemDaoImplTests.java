package com.music_server.mvp.dao.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;

import com.music_server.mvp.domain.PlaylistItem;

@ExtendWith(MockitoExtension.class)
public class PlaylistItemDaoImplTests {
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PlaylistItemDaoImpl test_playlist_item;



    @Test
    public void playlistItemCreationTest(){
        PlaylistItem playlist_item = new PlaylistItem(1L, 1L, 1);

        test_playlist_item.create(playlist_item);

        verify(jdbcTemplate).update(
            eq("INSERT INTO playlist_item (playlist_id, position, song_id) VALUES (?, ?, ?)"),
            eq(playlist_item.getPlaylist_id()), eq(playlist_item.getPosition()), eq(playlist_item.getSong_id())
        );
    }

    
}
