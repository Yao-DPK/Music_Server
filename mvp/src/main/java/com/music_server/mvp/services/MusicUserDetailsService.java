package com.music_server.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.music_server.mvp.domain.entities.UserEntity;
import com.music_server.mvp.repositories.UserRepository;
import com.music_server.mvp.security.MusicUserDetails;
import com.music_server.mvp.security.MusicUserDetails;

@Service
public class MusicUserDetailsService implements UserDetailsService {

    
    private final UserRepository userRepository;

    @Autowired
    public MusicUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));
        return new MusicUserDetails(user);
    }
    
}
