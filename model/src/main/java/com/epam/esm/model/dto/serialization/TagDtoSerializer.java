package com.epam.esm.model.dto.serialization;

import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.TagDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link DtoSerializer} for {@link Tag} entity.
 */
@Component
@Qualifier("tagDtoSerializer")
public class TagDtoSerializer implements DtoSerializer<TagDto, Tag> {
    @Override
    public TagDto dtoFromEntity(Tag entity) {
        return new TagDto(entity.getId(), entity.getName());
    }

    @Override
    public Tag dtoToEntity(TagDto dto) {
        return new Tag(dto.getId(), dto.getName());
    }
}
