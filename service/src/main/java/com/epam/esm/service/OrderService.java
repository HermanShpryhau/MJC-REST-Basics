package com.epam.esm.service;

import com.epam.esm.domain.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> fetchAllOrders(int page, int size);

    OrderDto fetchOrderById(Long id);

    OrderDto placeOrder(Long userId, Long certificateId, int quantity);
}
