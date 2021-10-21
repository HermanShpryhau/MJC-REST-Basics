package com.epam.esm.domain.dto.serialization;

import com.epam.esm.domain.AbstractEntity;

/**
 * Class serializes DTO to persisted entities and vice versa.
 * @param <D> Type of DTO
 * @param <E> Type of entity
 */
public interface DtoSerializer<D, E extends AbstractEntity> {
    D dtoFromEntity(E entity);

    E dtoToEntity(D dto);
}
