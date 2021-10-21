package com.epam.esm.persistence.repository;

import com.epam.esm.domain.AbstractEntity;

import java.util.List;

public interface CrudRepository<T extends AbstractEntity> {

    /**
     * Saves entity of type {@code T} to data source
     *
     * @param entity Entity object to save
     * @return Saved entity object from datasource
     */
    T save(T entity);

    /**
     * Finds all entities of type {@code T} in data source
     *
     * @return List of found entities
     */
    List<T> findAll(int page, int size);

    /**
     * Finds entity of type {@code T} by id in data source
     *
     * @param id ID of entity
     * @return Entity if it was found, {@code null} otherwise
     */
    T findById(Long id);

    /**
     * Updates entity of type {@code T} in data source
     *
     * @param entity Entity's new state
     * @return Updated entity
     */
    T update(T entity);

    /**
     * Deletes entity of type {@code T} from data source
     *
     * @param id ID of entity to delete
     * @return True if entity was deleted, false otherwise
     */
    boolean delete(Long id);
}
