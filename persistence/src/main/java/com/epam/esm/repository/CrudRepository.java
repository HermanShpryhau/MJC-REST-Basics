package com.epam.esm.repository;

import com.epam.esm.domain.AbstractEntity;

import java.util.List;

public interface CrudRepository<T extends AbstractEntity>{
    T save(T entity);

    List<T> findAll();

    T findById(Long id);

    T update(Long id, T entity);

    boolean delete(Long id);
}
