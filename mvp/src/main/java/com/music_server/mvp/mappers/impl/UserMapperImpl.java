package com.music_server.mvp.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.music_server.mvp.domain.dto.UserDto;
import com.music_server.mvp.domain.entities.UserEntity;

@Component
public class UserMapperImpl extends GenericMapperImpl<UserEntity, UserDto> {

    @Autowired
    public UserMapperImpl(ModelMapper modelMapper) {
        super(modelMapper, UserEntity.class, UserDto.class);
    }
}
