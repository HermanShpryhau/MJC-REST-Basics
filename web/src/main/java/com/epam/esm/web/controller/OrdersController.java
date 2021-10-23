package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    List<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return orderService.fetchAllOrders(page, size);
    }

    @GetMapping("/{id}")
    OrderDto getOrderById(@PathVariable Long id) {
        return orderService.fetchOrderById(id);
    }

    @PostMapping
    OrderDto placeOrder(@RequestParam("user") Long userId,
                        @RequestParam("certificate") Long certificateId,
                        @RequestParam("quantity") Integer quantity) {
        return orderService.placeOrder(userId, certificateId, quantity);
    }
}
