package com.epam.esm.service.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.serialization.OrderDtoSerializer;
import com.epam.esm.model.dto.serialization.UserDtoSerializer;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.service.UserService;
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
class UserServiceImplTest {
    private static final User[] TEST_USERS = {
            new User(1L, "User 1"),
            new User(2L, "User 2")
    };

    private static final UserDto[] TEST_USER_DTOS = {
            new UserDto(1L, "User 1"),
            new UserDto(2L, "User 2")
    };

    @Mock
    private UserRepository mockUserRepository;

    private UserDtoSerializer userDtoSerializer = new UserDtoSerializer();

    @Mock
    private OrderDtoSerializer orderDtoSerializer;

    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(mockUserRepository, userDtoSerializer, orderDtoSerializer);
    }

    @Test
    void fetchAllUsers() {
        Mockito.when(mockUserRepository.findAll(1, 10)).thenReturn(Arrays.asList(TEST_USERS));

        List<UserDto> tags = service.fetchAllUsers(1, 10);
        Assertions.assertEquals(Arrays.asList(TEST_USER_DTOS), tags);
    }

    @Test
    void fetchUserById() {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(TEST_USERS[0]);

        UserDto user = service.fetchUserById(1L);
        Assertions.assertEquals(TEST_USER_DTOS[0], user);
    }

    @Test
    void fetchNonExistingUserById() {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchUserById(1L));
    }

    @Test
    void fetchUserOrders() {
        GiftCertificate certificate = new GiftCertificate(1L, "Certificate 1", "Description 1", 1, 1,
                LocalDateTime.now(), LocalDateTime.now(), Collections.emptyList());
        GiftCertificateDto certificateDto = new GiftCertificateDto(1L, "Certificate 1", "Description 1", 1, 1,
                certificate.getCreateDate(), certificate.getLastUpdateDate(), Collections.emptyList());
        Order order = new Order(1L, TEST_USERS[0], certificate, 1, 1, LocalDateTime.now());
        OrderDto orderDto = new OrderDto(1L, TEST_USER_DTOS[0], certificateDto, 1, 1, order.getSubmissionDate());
        User userWithOrders = new User(1L, "User");
        userWithOrders.setOrders(Collections.singletonList(order));
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(userWithOrders);
        Mockito.when(orderDtoSerializer.dtoFromEntity(order)).thenReturn(orderDto);

        List<OrderDto> orders = service.fetchUserOrders(1L, 1, 10);
        Assertions.assertEquals(Collections.singletonList(orderDto), orders);
    }

    @Test
    void fetchNonExistingUserOrders() {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchUserOrders(1L, 1, 10));

    }
}