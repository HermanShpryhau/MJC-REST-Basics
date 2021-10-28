package com.epam.esm.web.hateoas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class GiftCertificateModel extends RepresentationModel<GiftCertificateModel> {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    public GiftCertificateModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;

        GiftCertificateModel model = (GiftCertificateModel) o;

        if (!id.equals(model.id)) return false;
        if (!name.equals(model.name)) return false;
        if (!description.equals(model.description)) return false;
        if (!price.equals(model.price)) return false;
        if (!duration.equals(model.duration)) return false;
        if (!createDate.equals(model.createDate)) return false;
        return lastUpdateDate.equals(model.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificateModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
