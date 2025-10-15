package com.music_server.mvp.services;

import java.util.List;
import java.util.Optional;

public interface GenericService<E, ID> {
    E create(E entity);

    List<E> findAll();

    Optional<E> findById(ID id);

    boolean existById(ID id);
    
    void delete(ID id);
    
}
