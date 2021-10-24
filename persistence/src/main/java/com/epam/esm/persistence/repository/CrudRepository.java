package com.epam.esm.persistence.repository;

import com.epam.esm.domain.AbstractEntity;

import java.util.List;

public interface CrudRepository<T extends AbstractEntity> {

    /**
     * Saves entity of type {@code T} to data source.
     *
     * @param entity Entity object to save
     * @return Saved entity object from datasource
     */
    default T save(T entity) {
        throw new RepositoryException(RepositoryErrorCode.OPERATION_NOT_SUPPORTED, "SAVE");
    }

    /**
     * Finds all entities of type {@code T} in data source.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of found entities
     */
    default List<T> findAll(int page, int size) {
        throw new RepositoryException(RepositoryErrorCode.OPERATION_NOT_SUPPORTED, "FIND ALL");
    }

    /**
     * Finds entity of type {@code T} by id in data source.
     *
     * @param id ID of entity
     * @return Entity if it was found, {@code null} otherwise
     */
    default T findById(Long id) {
        throw new RepositoryException(RepositoryErrorCode.OPERATION_NOT_SUPPORTED, "FIND BY ID");
    }

    /**
     * Updates entity of type {@code T} in data source.
     *
     * @param entity Entity's new state
     * @return Updated entity
     */
    default T update(T entity) {
        throw new RepositoryException(RepositoryErrorCode.OPERATION_NOT_SUPPORTED, "UPDATE");
    }

    /**
     * Deletes entity of type {@code T} from data source.
     *
     * @param id ID of entity to delete
     * @return True if entity was deleted, false otherwise
     */
    default boolean delete(Long id) {
        throw new RepositoryException(RepositoryErrorCode.OPERATION_NOT_SUPPORTED, "DELETE");
    }

    int countAll();
}
