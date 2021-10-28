package com.epam.esm.web.controller;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.web.hateoas.assembler.OrderModelAssembler;
import com.epam.esm.web.hateoas.model.OrderModel;
import com.epam.esm.web.hateoas.processor.OrderModelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of orders resource.
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;
    private final OrderModelAssembler orderModelAssembler;
    private final OrderModelProcessor orderModelProcessor;

    @Autowired
    public OrdersController(OrderService orderService,
                            OrderModelAssembler orderModelAssembler,
                            OrderModelProcessor orderModelProcessor) {
        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
        this.orderModelProcessor = orderModelProcessor;
    }

    /**
     * Gets all orders by page.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of order DTOs on page.
     */
    @GetMapping
    public CollectionModel<OrderModel> getAllOrders(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        List<OrderDto> orderDtos = orderService.fetchAllOrders(page, size);
        CollectionModel<OrderModel> collectionModel = orderModelAssembler.toCollectionModel(orderDtos);
        return orderModelProcessor.process(page, size, collectionModel);
    }

    /**
     * Gets order by id.
     *
     * @param id ID of order to get
     * @return Order DTO with given id
     */
    @GetMapping("/{id}")
    public OrderModel getOrderById(@PathVariable Long id) {
        OrderDto orderDto = orderService.fetchOrderById(id);
        return orderModelAssembler.toModel(orderDto);
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
    public OrderModel placeOrder(@RequestParam("user") Long userId,
                        @RequestParam("certificate") Long certificateId,
                        @RequestParam("quantity") Integer quantity) {
        OrderDto orderDto = orderService.placeOrder(userId, certificateId, quantity);
        return orderModelAssembler.toModel(orderDto);
    }
}
