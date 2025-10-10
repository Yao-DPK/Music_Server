package com.music_server.mvp.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.music_server.mvp.domain.dto.PlaylistItemDto;
import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.PlaylistItemEntity;
import com.music_server.mvp.domain.entities.UserEntity;

public class PlaylistItemMapperImpl extends GenericMapperImpl<PlaylistItemEntity, PlaylistItemDto>{
    @Autowired
    public PlaylistItemMapperImpl(ModelMapper modelMapper) {
        super(modelMapper, PlaylistItemEntity.class, PlaylistItemDto.class);
    }
}
