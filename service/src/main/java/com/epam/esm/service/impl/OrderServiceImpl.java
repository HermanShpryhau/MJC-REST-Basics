package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.serialization.DtoSerializer;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository certificateRepository;
    private final DtoSerializer<OrderDto, Order> orderDtoSerializer;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            GiftCertificateRepository certificateRepository,
                            @Qualifier("orderDtoSerializer") DtoSerializer<OrderDto, Order> orderDtoSerializer) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.orderDtoSerializer = orderDtoSerializer;
    }

    @Override
    public List<OrderDto> fetchAllOrders(int page, int size) {
        return orderRepository.findAll(page, size).stream()
                .map(orderDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto fetchOrderById(Long id) {
        // TODO Add error code
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new ServiceException("1"));
        return orderDtoSerializer.dtoFromEntity(order);
    }

    @Override
    @Transactional
    public OrderDto placeOrder(Long userId, Long certificateId, int quantity) {
        // TODO Add error code
        User user = Optional.ofNullable(userRepository.findById(userId))
                .orElseThrow(() -> new ServiceException("1"));

        // TODO Add error code
        GiftCertificate certificate = Optional.ofNullable(certificateRepository.findById(certificateId))
                .orElseThrow(() -> new ServiceException("1"));

        int totalPrice = calculateTotalPrice(quantity, certificate);
        Order order = orderRepository.save(new Order(user, certificate, quantity, totalPrice, LocalDateTime.now()));
        return orderDtoSerializer.dtoFromEntity(order);
    }

    private int calculateTotalPrice(int quantity, GiftCertificate certificate) {
        return certificate.getPrice() * quantity;
    }
}
