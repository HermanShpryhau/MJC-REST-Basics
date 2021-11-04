package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.pagination.Page;

/**
 * {@code OrderService} is an interface that contains all operations available for orders resource of the API.
 */
public interface OrderService {

    /**
     * Fetches list of order DTOs on page.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of order DTOs on page
     */
    Page<OrderDto> fetchAllOrders(int page, int size);

    /**
     * Fetches order by ID.
     *
     * @param id ID of order to fetch.
     * @return Order DTO with given ID.
     */
    OrderDto fetchOrderById(Long id);

    /**
     * Places a new order on gift certificate
     *
     * @param userId        ID of user
     * @param certificateId ID of gift certificate
     * @param quantity      Quantity of certificates
     * @return Placed order DTO
     */
    OrderDto placeOrder(Long userId, Long certificateId, int quantity);
}
