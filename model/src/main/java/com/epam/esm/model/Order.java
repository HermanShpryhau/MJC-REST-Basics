package com.epam.esm.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private GiftCertificate giftCertificate;

    @Column(name = "quantity")
    @NotNull
    @Positive
    private Integer quantity;

    @Column(name = "total_price")
    @NotNull
    @Positive
    private Integer totalPrice;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    public Order() {}

    public Order(User user, GiftCertificate giftCertificate, Integer quantity, Integer totalPrice,
                 LocalDateTime submissionDate) {
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.submissionDate = submissionDate;
    }

    public Order(Long id, User user, GiftCertificate giftCertificate, Integer quantity, Integer totalPrice,
                 LocalDateTime submissionDate) {
        super(id);
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.submissionDate = submissionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
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
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (!user.equals(order.user)) return false;
        if (!giftCertificate.equals(order.giftCertificate)) return false;
        if (!quantity.equals(order.quantity)) return false;
        if (!totalPrice.equals(order.totalPrice)) return false;
        return submissionDate.equals(order.submissionDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + giftCertificate.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + totalPrice.hashCode();
        result = 31 * result + submissionDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", giftCertificate=" + giftCertificate +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", submissionDate=" + submissionDate +
                '}';
    }
}
