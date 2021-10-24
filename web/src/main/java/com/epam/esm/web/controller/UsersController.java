package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of users resource.
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets all users on page.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of user DTOs on page
     */
    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.fetchAllUsers(page, size);
    }

    /**
     * Gets user by id.
     *
     * @param id ID of user
     * @return User DTO with given ID
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.fetchUserById(id);
    }

    /**
     * Gets user's orders on page.
     *
     * @param id   ID of user
     * @param page Index of page
     * @param size Size of page
     * @return List of order DTOs on page.
     */
    @GetMapping("/{id}/orders")
    public List<OrderDto> getUserOrders(@PathVariable Long id,
                                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.fetchUserOrders(id, page, size);
    }
}
