package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.serialization.DtoSerializer;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Page<UserDto> fetchAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(userDtoSerializer::dtoFromEntity);
    }

    @Override
    public UserDto fetchUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND, id));
        return userDtoSerializer.dtoFromEntity(user);
    }

    @Override
    @Transactional
    public Page<OrderDto> fetchUserOrders(Long id, int page, int size) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND, id));
        List<Order> orders = user.getOrders();
        List<OrderDto> orderDtos = user.getOrders().stream()
                .skip((long) (page) * size)
                .limit(size)
                .map(orderDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(orderDtos, PageRequest.of(page, size), orders.size());
    }
}
