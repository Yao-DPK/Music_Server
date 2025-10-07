package com.music_server.mvp;

import com.music_server.mvp.domain.Song;
import com.music_server.mvp.domain.User;

import com.music_server.mvp.domain.Playlist;
import com.music_server.mvp.domain.PlaylistItem;

public final class TestDataUtil {

    private TestDataUtil(){

    }

    public static Song createTestSong(String title){
        return  new Song(title);
    }

    public static User createTestUser(String username, String password){
        return new User(username, password);
    }

    public static Playlist createTestPlaylist(String title){
        return new Playlist(title);
    }

    public static PlaylistItem createTestPlaylistItem(Long playlist_id, Long song_id){
        return new PlaylistItem(playlist_id, song_id);
    }

    
}
