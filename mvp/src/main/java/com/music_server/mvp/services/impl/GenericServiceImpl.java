package com.music_server.mvp.services.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.music_server.mvp.services.GenericService;

import jakarta.transaction.Transactional;

public abstract class GenericServiceImpl<E, ID, R extends JpaRepository<E, ID>> implements GenericService<E, ID> {

    protected final R repository;

    protected GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @Transactional
    public E create(E user) {
        return repository.save(user);
    }

    
    public Page<E> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Transactional
    public Optional<E> findById(ID id){
        return repository.findById(id);
    }

    @Transactional
    public boolean existById(ID id){
        return repository.existsById(id);
    }

    @Transactional
    public void delete(ID id){
        repository.deleteById(id);
    }
}
