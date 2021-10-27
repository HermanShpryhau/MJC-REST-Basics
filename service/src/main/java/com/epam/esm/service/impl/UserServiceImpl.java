package com.epam.esm.service.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.serialization.DtoSerializer;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoSerializer<UserDto, User> userDtoSerializer;
    private final DtoSerializer<OrderDto, Order> orderDtoSerializer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           @Qualifier("userDtoSerializer") DtoSerializer<UserDto, User> userDtoSerializer,
                           @Qualifier("orderDtoSerializer") DtoSerializer<OrderDto, Order> orderDtoSerializer) {
        this.userRepository = userRepository;
        this.userDtoSerializer = userDtoSerializer;
        this.orderDtoSerializer = orderDtoSerializer;
    }

    @Override
    public List<UserDto> fetchAllUsers(int page, int size) {
        page = PaginationUtil.correctPage(page, size, userRepository::countAll);
        return userRepository.findAll(page, size).stream()
                .map(userDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto fetchUserById(Long id) {
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND, id));
        return userDtoSerializer.dtoFromEntity(user);
    }

    @Override
    public List<OrderDto> fetchUserOrders(Long id, int page, int size) {
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND, id));
        page = PaginationUtil.correctPage(page, size, user.getOrders()::size);
        return user.getOrders().stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .map(orderDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
    }
}
