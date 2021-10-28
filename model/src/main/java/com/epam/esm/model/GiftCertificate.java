package com.epam.esm.model;

import com.epam.esm.model.validation.DtoTag;
import com.epam.esm.model.validation.PatchDto;
import com.epam.esm.model.validation.SaveDto;
import com.epam.esm.model.validation.ValidationErrorCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Gift_certificate")
public class GiftCertificate extends AbstractEntity {
    @Column(name = "name")
    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_NAME_NOT_NULL)
    @Size(min = 1, max = 45, groups = {SaveDto.class, PatchDto.class}, message =
            ValidationErrorCode.INVALID_CERTIFICATE_NAME_LENGTH)
    private String name;

    @Column(name = "description")
    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_DESCRIPTION_NOT_NULL)
    @Size(min = 1, max = 300, groups = {SaveDto.class, PatchDto.class}, message =
            ValidationErrorCode.INVALID_CERTIFICATE_DESCRIPTION_LENGTH)
    private String description;

    @Column(name = "price")
    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_PRICE_NOT_NULL)
    @Positive(groups = {SaveDto.class, PatchDto.class}, message = ValidationErrorCode.INVALID_CERTIFICATE_PRICE)
    private int price;

    @Column(name = "duration")
    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_DURATION_NOT_NULL)
    @Positive(groups = {SaveDto.class, PatchDto.class}, message = ValidationErrorCode.INVALID_CERTIFICATE_DURATION)
    private int duration;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Gift_certificate_has_Tag",
            joinColumns = @JoinColumn(name = "certificate"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    private List<@DtoTag(groups = {SaveDto.class, PatchDto.class}) Tag> associatedTags = new ArrayList<>();

    public GiftCertificate() {
    }

    public GiftCertificate(Long id, String name, String description, int price, int duration, LocalDateTime createDate,
                           LocalDateTime lastUpdateDate, List<Tag> associatedTags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.associatedTags = associatedTags;
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

    public List<Tag> getAssociatedTags() {
        return associatedTags;
    }

    public void setAssociatedTags(List<Tag> associatedTags) {
        this.associatedTags = associatedTags;
    }

    @PreUpdate
    public void setLastUpdateDate() {
        lastUpdateDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (price != that.price) return false;
        if (duration != that.duration) return false;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        if (!createDate.equals(that.createDate)) return false;
        if (!lastUpdateDate.equals(that.lastUpdateDate)) return false;
        return associatedTags.equals(that.associatedTags);
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
        result = 31 * result + associatedTags.hashCode();
        return result;
    }
}
