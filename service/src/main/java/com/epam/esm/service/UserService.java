package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.pagination.Page;

/**
 * {@code UserService} is an interface that contains all operations available for users resource of the API.
 */
public interface UserService {

    /**
     * Fetches all users on page.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of user DTOs on page
     */
    Page<UserDto> fetchAllUsers(int page, int size);

    /**
     * Fetches user by ID.
     *
     * @param id User ID
     * @return User DTO with given ID
     */
    UserDto fetchUserById(Long id);

    /**
     * Fetches user's orders on page.
     *
     * @param id   User ID
     * @param page Index of page
     * @param size Size of page
     * @return List of user's orders DTOs on page
     */
    Page<OrderDto> fetchUserOrders(Long id, int page, int size);
}
