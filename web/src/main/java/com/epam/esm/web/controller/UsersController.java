package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.fetchAllUsers(page, size);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.fetchUserById(id);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> getUserOrders(@PathVariable Long id,
                                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.fetchUserOrders(id, page, size);
    }
}
