package com.music_server.mvp.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.music_server.mvp.domain.dto.PlaylistDto;
import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.PlaylistEntity;
import com.music_server.mvp.domain.entities.UserEntity;

@Component
public class PlaylistMapperImpl extends GenericMapperImpl<PlaylistEntity, PlaylistDto>{
    @Autowired
    public PlaylistMapperImpl(ModelMapper modelMapper) {
        super(modelMapper, PlaylistEntity.class, PlaylistDto.class);
    }
}
