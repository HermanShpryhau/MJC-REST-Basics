package com.epam.esm.model.dto.serialization;

import com.epam.esm.model.User;
import com.epam.esm.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link DtoSerializer} for {@link User} entity.
 */
@Component
@Qualifier("userDtoSerializer")
public class UserDtoSerializer implements DtoSerializer<UserDto, User> {
    @Override
    public UserDto dtoFromEntity(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public User dtoToEntity(UserDto dto) {
        return null;
    }
}
