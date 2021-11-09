package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.serialization.OrderDtoSerializer;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static final User[] TEST_USERS = {
            new User(1L, "User 1"),
            new User(2L, "User 2")
    };

    private static final UserDto[] TEST_USER_DTOS = {
            new UserDto(1L, "User 1"),
            new UserDto(2L, "User 2")
    };

    private static final GiftCertificate[] TEST_CERTIFICATES = {
            new GiftCertificate(1L, "Certificate 1", "Description 1", 1, 1,
                    LocalDateTime.now(), LocalDateTime.now(), Collections.emptySet()),
            new GiftCertificate(2L, "Certificate 2", "Description 2", 2, 2,
                    LocalDateTime.now(), LocalDateTime.now(), Collections.emptySet())
    };

    private static final GiftCertificateDto[] TEST_CERTIFICATE_DTOS = {
            new GiftCertificateDto(TEST_CERTIFICATES[0].getId(), TEST_CERTIFICATES[0].getName(),
                    TEST_CERTIFICATES[0].getDescription(), TEST_CERTIFICATES[0].getPrice(),
                    TEST_CERTIFICATES[0].getDuration(), TEST_CERTIFICATES[0].getCreateDate(),
                    TEST_CERTIFICATES[0].getLastUpdateDate(), Collections.emptyList()),
            new GiftCertificateDto(TEST_CERTIFICATES[1].getId(), TEST_CERTIFICATES[1].getName(),
                    TEST_CERTIFICATES[1].getDescription(), TEST_CERTIFICATES[1].getPrice(),
                    TEST_CERTIFICATES[1].getDuration(), TEST_CERTIFICATES[1].getCreateDate(),
                    TEST_CERTIFICATES[1].getLastUpdateDate(), Collections.emptyList())
    };

    private static final Order[] TEST_ORDERS = {
            new Order(1L, TEST_USERS[0], TEST_CERTIFICATES[0], 2, 2, LocalDateTime.now()),
            new Order(2L, TEST_USERS[1], TEST_CERTIFICATES[1], 2, 1, LocalDateTime.now())
    };

    private static final OrderDto[] TEST_ORDER_DTOS = {
            new OrderDto(1L, TEST_USER_DTOS[0], TEST_CERTIFICATE_DTOS[0], 1, 2, TEST_ORDERS[0].getSubmissionDate()),
            new OrderDto(2L, TEST_USER_DTOS[1], TEST_CERTIFICATE_DTOS[1], 2, 1, TEST_ORDERS[1].getSubmissionDate())
    };

    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private GiftCertificateRepository mockCertificateRepository;

    @Mock
    private OrderDtoSerializer orderDtoSerializer;

    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderServiceImpl(mockOrderRepository, mockUserRepository, mockCertificateRepository,
                orderDtoSerializer);
    }

    @Test
    void fetchAllOrdersTest() {
        Mockito.when(mockOrderRepository.findAll(1, 10)).thenReturn(Arrays.asList(TEST_ORDERS));
        Mockito.when(orderDtoSerializer.dtoFromEntity(TEST_ORDERS[0])).thenReturn(TEST_ORDER_DTOS[0]);
        Mockito.when(orderDtoSerializer.dtoFromEntity(TEST_ORDERS[1])).thenReturn(TEST_ORDER_DTOS[1]);

        List<OrderDto> orders = service.fetchAllOrders(1, 10).getContent();
        Assertions.assertEquals(Arrays.asList(TEST_ORDER_DTOS), orders);
    }

    @Test
    void fetchOrderByIdTest() {
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(TEST_ORDERS[0]);
        Mockito.when(orderDtoSerializer.dtoFromEntity(TEST_ORDERS[0])).thenReturn(TEST_ORDER_DTOS[0]);

        OrderDto order = service.fetchOrderById(1L);
        Assertions.assertEquals(TEST_ORDER_DTOS[0], order);
    }

    @Test
    void fetchNonExistingOrderByIdTest() {
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchOrderById(1L));
    }

    @Test
    void placeOrderTest() {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(TEST_USERS[0]);
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(TEST_CERTIFICATES[0]);
        Mockito.when(mockOrderRepository.save(Mockito.any(Order.class))).thenReturn(TEST_ORDERS[0]);
        Mockito.when(orderDtoSerializer.dtoFromEntity(TEST_ORDERS[0])).thenReturn(TEST_ORDER_DTOS[0]);
        OrderDto order = service.placeOrder(1L, 1L, 2);
        Assertions.assertEquals(TEST_ORDER_DTOS[0], order);
    }

    @Test
    void placeOrderForNonExistingUserTest() {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.placeOrder(1L, 1L, 1));
    }

    @Test
    void placeOrderForNonExistingCertificateTest() {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(TEST_USERS[0]);
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.placeOrder(1L, 1L, 1));
    }
}