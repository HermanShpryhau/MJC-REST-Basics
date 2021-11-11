package com.epam.esm.web.controller;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.web.hateoas.assembler.OrderModelAssembler;
import com.epam.esm.web.hateoas.assembler.UserModelAssembler;
import com.epam.esm.web.hateoas.model.OrderModel;
import com.epam.esm.web.hateoas.model.UserModel;
import com.epam.esm.web.hateoas.processor.OrderModelProcessor;
import com.epam.esm.web.hateoas.processor.UserModelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of users resource.
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final UserModelProcessor userModelProcessor;
    private final OrderModelAssembler orderModelAssembler;
    private final OrderModelProcessor orderModelProcessor;

    @Autowired
    public UsersController(UserService userService,
                           UserModelAssembler userModelAssembler,
                           UserModelProcessor userModelProcessor,
                           OrderModelAssembler orderModelAssembler,
                           OrderModelProcessor orderModelProcessor) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.userModelProcessor = userModelProcessor;
        this.orderModelAssembler = orderModelAssembler;
        this.orderModelProcessor = orderModelProcessor;
    }

    /**
     * Gets all users on page.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of user DTOs on page
     */
    @GetMapping
    public CollectionModel<UserModel> getAllUsers(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<UserDto> userDtos = userService.fetchAllUsers(page, size);
        CollectionModel<UserModel> collectionModel = userModelAssembler.toCollectionModel(userDtos.getContent());
        return userModelProcessor.process(userDtos, size, collectionModel);
    }

    /**
     * Gets user by id.
     *
     * @param id ID of user
     * @return User DTO with given ID
     */
    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable Long id) {
        UserDto userDto = userService.fetchUserById(id);
        return userModelAssembler.toModel(userDto);
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
    public CollectionModel<OrderModel> getUserOrders(@PathVariable Long id,
                                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<OrderDto> orderDtos = userService.fetchUserOrders(id, page, size);
        CollectionModel<OrderModel> collectionModel = orderModelAssembler.toCollectionModel(orderDtos.getContent());
        return orderModelProcessor.process(id, orderDtos, size, collectionModel);
    }
}
