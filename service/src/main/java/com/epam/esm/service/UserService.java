package com.epam.esm.service;

import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> fetchAllUsers(int page, int size);

    UserDto fetchUserById(Long id);

    List<OrderDto> fetchUserOrders(Long id, int page, int size);
}
