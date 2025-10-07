package com.music_server.mvp;

import com.music_server.mvp.domain.Song;
import com.music_server.mvp.domain.User;

import com.music_server.mvp.domain.Playlist;
import com.music_server.mvp.domain.PlaylistItem;

public final class TestDataUtil {

    private TestDataUtil(){

    }

    public static Song createTestSong(String title, User user){
        return  new Song(title, user);
    }

    public static User createTestUser(String username, String password){
        return new User(username, password);
    }

    public static Playlist createTestPlaylist(String title, User user){
        return new Playlist(title, user);
    }

}
