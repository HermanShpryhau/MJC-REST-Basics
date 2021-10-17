package com.epam.esm.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Gift_certificate")
public class GiftCertificate extends AbstractEntity {
    private String name;
    private String description;
    private int price;
    private int duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;

    public GiftCertificate() {
    }

    public GiftCertificate(Long id, String name, String description, int price, int duration, Timestamp createDate,
                           Timestamp lastUpdateDate) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificate)) return false;
        if (!super.equals(o)) return false;

        GiftCertificate other = (GiftCertificate) o;

        if (!getId().equals(other.getId())) return false;

        if (price != other.price) return false;
        if (duration != other.duration) return false;
        if (!name.equals(other.name)) return false;
        if (!description.equals(other.description)) return false;
        if (!createDate.equals(other.createDate)) return false;
        return lastUpdateDate.equals(other.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price;
        result = 31 * result + duration;
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
