package com.epam.esm.model.dto;

import com.epam.esm.model.validation.ValidationErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements DataTransferObject {
    private Long id;

    @NotNull(message = ValidationErrorCode.ORDER_USER_NOT_NULL)
    private UserDto user;

    @NotNull(message = ValidationErrorCode.ORDER_CERTIFICATE_NOT_NULL)
    private GiftCertificateDto giftCertificate;

    @NotNull(message = ValidationErrorCode.ORDER_QUANTITY_NOT_NULL)
    @Positive(message = ValidationErrorCode.INVALID_ORDER_QUANTITY)
    private Integer quantity;

    private Integer totalPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime submissionDate;
}
