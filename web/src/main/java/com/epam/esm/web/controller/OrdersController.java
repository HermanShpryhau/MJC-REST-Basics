package com.epam.esm.web.controller;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of orders resource.
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Gets all orders by page.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of order DTOs on page.
     */
    @GetMapping
    List<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return orderService.fetchAllOrders(page, size);
    }

    /**
     * Gets order by id.
     *
     * @param id ID of order to get
     * @return Order DTO with given id
     */
    @GetMapping("/{id}")
    OrderDto getOrderById(@PathVariable Long id) {
        return orderService.fetchOrderById(id);
    }

    /**
     * Places a new order.
     *
     * @param userId        ID of user placing the order
     * @param certificateId ID of certificate in order
     * @param quantity      Quantity of certificates beeing bought
     * @return Placed order DTO
     */
    @PostMapping
    OrderDto placeOrder(@RequestParam("user") Long userId,
                        @RequestParam("certificate") Long certificateId,
                        @RequestParam("quantity") Integer quantity) {
        return orderService.placeOrder(userId, certificateId, quantity);
    }
}
