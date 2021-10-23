package com.epam.esm.domain.dto;

import com.epam.esm.domain.validation.ValidationErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class OrderDto {
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

    public OrderDto() {}

    public OrderDto(Long id, UserDto user, GiftCertificateDto giftCertificate, Integer quantity, Integer totalPrice,
                    LocalDateTime submissionDate) {
        this.id = id;
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.submissionDate = submissionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public GiftCertificateDto getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificateDto giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        if (!id.equals(orderDto.id)) return false;
        if (!user.equals(orderDto.user)) return false;
        if (!giftCertificate.equals(orderDto.giftCertificate)) return false;
        if (!quantity.equals(orderDto.quantity)) return false;
        if (!totalPrice.equals(orderDto.totalPrice)) return false;
        return submissionDate.equals(orderDto.submissionDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + giftCertificate.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + totalPrice.hashCode();
        result = 31 * result + submissionDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", user=" + user +
                ", giftCertificate=" + giftCertificate +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", submissionDate=" + submissionDate +
                '}';
    }
}
