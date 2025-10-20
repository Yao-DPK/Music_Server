package com.music_server.mvp.mappers.impl;


import org.modelmapper.ModelMapper;

import com.music_server.mvp.mappers.Mapper;


public abstract class GenericMapperImpl<T, K> implements Mapper<T, K>{

    protected final ModelMapper modelMapper;
    private final Class<T> entityClass;
    private final Class<K> dtoClass;

    protected GenericMapperImpl(ModelMapper modelMapper, Class<T> entityClass, Class<K> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public K mapTo(T t) {
        return modelMapper.map(t, dtoClass);
    }

    @Override
    public T mapFrom(K k) {
        return modelMapper.map(k, entityClass);
    }

}
