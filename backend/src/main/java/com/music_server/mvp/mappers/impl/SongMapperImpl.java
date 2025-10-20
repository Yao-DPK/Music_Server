package com.music_server.mvp.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.music_server.mvp.domain.dto.SongDto;
import com.music_server.mvp.domain.entities.SongEntity;

@Component
public class SongMapperImpl extends GenericMapperImpl<SongEntity, SongDto> {
    @Autowired
    public SongMapperImpl(ModelMapper modelMapper){
        super(modelMapper, SongEntity.class, SongDto.class);
    }
}
