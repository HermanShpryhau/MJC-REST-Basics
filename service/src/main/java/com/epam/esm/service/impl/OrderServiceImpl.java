package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.serialization.DtoSerializer;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.service.pagination.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link OrderService}.
 */
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
    public Page<OrderDto> fetchAllOrders(int page, int size) {
        page = PaginationUtil.correctPageIndex(page, size, orderRepository::countAll);
        List<OrderDto> orderDtos = orderRepository.findAll(page, size).stream()
                .map(orderDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
        return new Page<>(page, size, orderRepository.countAll(), orderDtos);
    }

    @Override
    public OrderDto fetchOrderById(Long id) {
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.ORDER_NOT_FOUND, id));
        return orderDtoSerializer.dtoFromEntity(order);
    }

    @Override
    @Transactional
    public OrderDto placeOrder(Long userId, Long certificateId, int quantity) {
        User user = Optional.ofNullable(userRepository.findById(userId))
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.USER_NOT_FOUND, userId));
        GiftCertificate certificate = Optional.ofNullable(certificateRepository.findById(certificateId))
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.CERTIFICATE_NOT_FOUND, certificateId));

        if (quantity < 0) {
            throw new ServiceException(ServiceErrorCode.INVALID_QUANTITY);
        }

        int totalPrice = calculateTotalPrice(quantity, certificate);
        Order order = orderRepository.save(new Order(user, certificate, quantity, totalPrice, LocalDateTime.now()));
        return orderDtoSerializer.dtoFromEntity(order);
    }

    /**
     * Calculates total price of order.
     *
     * @param quantity    Quantity of certificates in order
     * @param certificate Gift certificate entity
     * @return Total price of order.
     */
    private int calculateTotalPrice(int quantity, GiftCertificate certificate) {
        return certificate.getPrice() * quantity;
    }
}
