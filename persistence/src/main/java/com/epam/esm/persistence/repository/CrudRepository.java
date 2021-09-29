package com.epam.esm.persistence.repository;

import com.epam.esm.domain.AbstractEntity;

import java.util.List;

public interface CrudRepository<T extends AbstractEntity>{
    T save(T entity) throws RepositoryException;

    List<T> findAll() throws RepositoryException;

    T findById(Long id) throws RepositoryException;

    T update(Long id, T entity) throws RepositoryException;

    boolean delete(Long id) throws RepositoryException;
}
