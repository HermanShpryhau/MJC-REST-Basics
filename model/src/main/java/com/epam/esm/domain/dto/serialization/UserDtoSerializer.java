package com.epam.esm.domain.dto.serialization;

import com.epam.esm.domain.User;
import com.epam.esm.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
        // TODO Check if it's needed
        return null;
    }
}
