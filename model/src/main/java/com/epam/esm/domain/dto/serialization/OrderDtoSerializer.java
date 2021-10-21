package com.epam.esm.domain.dto.serialization;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("orderDtoSerializer")
public class OrderDtoSerializer implements DtoSerializer<OrderDto, Order> {
    private final DtoSerializer<UserDto, User> userDtoSerializer;
    private final DtoSerializer<GiftCertificateDto, GiftCertificate> certificateDtoSerializer;

    @Autowired
    public OrderDtoSerializer(@Qualifier("userDtoSerializer") DtoSerializer<UserDto, User> userDtoSerializer,
                              @Qualifier("giftCertificateDtoSerializer") DtoSerializer<GiftCertificateDto, GiftCertificate> certificateDtoSerializer) {
        this.userDtoSerializer = userDtoSerializer;
        this.certificateDtoSerializer = certificateDtoSerializer;
    }

    @Override
    public OrderDto dtoFromEntity(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setUser(userDtoSerializer.dtoFromEntity(entity.getUser()));
        dto.setGiftCertificate(certificateDtoSerializer.dtoFromEntity(entity.getGiftCertificate()));
        dto.setQuantity(entity.getQuantity());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setSubmissionDate(entity.getSubmissionDate());
        return dto;
    }

    @Override
    public Order dtoToEntity(OrderDto dto) {
        // TODO Check if it's needed
        return null;
    }
}
