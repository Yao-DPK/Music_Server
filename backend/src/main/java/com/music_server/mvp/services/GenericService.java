package com.music_server.mvp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface GenericService<E, ID> {
    E create(E entity);

    Page<E> findAll(Pageable pageable);

    Optional<E> findById(ID id);

    boolean existById(ID id);
    
    void delete(ID id);
    
}
