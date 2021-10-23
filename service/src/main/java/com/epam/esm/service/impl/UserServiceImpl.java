package com.epam.esm.service.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.dto.serialization.DtoSerializer;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return userRepository.findAll(page, size).stream()
                .map(userDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto fetchUserById(Long id) {
        return userDtoSerializer.dtoFromEntity(userRepository.findById(id));
    }

    @Override
    public List<OrderDto> fetchUserOrders(Long id, int page, int size) {
        // TODO add error code
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new ServiceException("1", id));
        List<OrderDto> orders = user.getOrders().stream()
                .map(orderDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
        return orders;
    }

    @Override
    public OrderDto placeOrder(OrderDto order) {
        return null;
    }
}