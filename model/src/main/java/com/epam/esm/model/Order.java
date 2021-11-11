package com.epam.esm.model;

import com.epam.esm.model.audit.OrderAuditingListener;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
//@EntityListeners(OrderAuditingListener.class)
public class Order implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
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
}
