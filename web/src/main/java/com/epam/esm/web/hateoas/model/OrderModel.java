package com.epam.esm.web.hateoas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(itemRelation = "order", collectionRelation = "orders")
public class OrderModel extends RepresentationModel<OrderModel> {
    private Long id;
    private Long userId;
    private Long certificateId;
    private Integer totalPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime submissionDate;

    public OrderModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
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

        OrderModel model = (OrderModel) o;

        if (!id.equals(model.id)) return false;
        if (!userId.equals(model.userId)) return false;
        if (!certificateId.equals(model.certificateId)) return false;
        if (!totalPrice.equals(model.totalPrice)) return false;
        return submissionDate.equals(model.submissionDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + certificateId.hashCode();
        result = 31 * result + totalPrice.hashCode();
        result = 31 * result + submissionDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", certificateId=" + certificateId +
                ", totalPrice=" + totalPrice +
                ", submissionDate=" + submissionDate +
                '}';
    }
}
