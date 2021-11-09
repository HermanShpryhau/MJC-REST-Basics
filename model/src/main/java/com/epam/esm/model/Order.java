package com.epam.esm.model;

import com.epam.esm.model.audit.OrderAuditingListener;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
@EntityListeners(OrderAuditingListener.class)
public class Order implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Order(User user, GiftCertificate giftCertificate, Integer quantity, Integer totalPrice,
                 LocalDateTime submissionDate) {
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.submissionDate = submissionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;
        if (!user.equals(order.user)) return false;
        if (!giftCertificate.equals(order.giftCertificate)) return false;
        if (!quantity.equals(order.quantity)) return false;
        if (!totalPrice.equals(order.totalPrice)) return false;
        return submissionDate.equals(order.submissionDate);
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
}
