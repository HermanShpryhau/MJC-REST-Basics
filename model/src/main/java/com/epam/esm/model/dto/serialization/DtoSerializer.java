package com.epam.esm.model.dto.serialization;

import com.epam.esm.model.AbstractEntity;

/**
 * Class serializes DTO to persisted entities and vice versa.
 * @param <D> Type of DTO
 * @param <E> Type of entity
 */
public interface DtoSerializer<D, E extends AbstractEntity> {
    /**
     * Serializes a DTO.
     * @param entity Entity to serialize
     * @return DTO serialized from entity
     */
    D dtoFromEntity(E entity);

    /**
     * Deserializes a DTO int an entity.
     * @param dto DTO to deserialize
     * @return Entity created from DTO
     */
    E dtoToEntity(D dto);
}
