package com.epam.esm.model.dto;

import com.epam.esm.model.validation.DtoTag;
import com.epam.esm.model.validation.PatchDto;
import com.epam.esm.model.validation.SaveDto;
import com.epam.esm.model.validation.ValidationErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateDto implements DataTransferObject {
    private Long id;

    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_NAME_NOT_NULL)
    @Size(min = 1, max = 45, groups = {SaveDto.class, PatchDto.class}, message =
            ValidationErrorCode.INVALID_CERTIFICATE_NAME_LENGTH)
    private String name;

    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_DESCRIPTION_NOT_NULL)
    @Size(min = 1, max = 300, groups = {SaveDto.class, PatchDto.class}, message =
            ValidationErrorCode.INVALID_CERTIFICATE_DESCRIPTION_LENGTH)
    private String description;

    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_PRICE_NOT_NULL)
    @Positive(groups = {SaveDto.class, PatchDto.class}, message = ValidationErrorCode.INVALID_CERTIFICATE_PRICE)
    private Integer price;

    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_DURATION_NOT_NULL)
    @Positive(groups = {SaveDto.class, PatchDto.class}, message = ValidationErrorCode.INVALID_CERTIFICATE_DURATION)
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @NotNull(groups = {SaveDto.class}, message = ValidationErrorCode.DTO_TAG_LIST_NOT_NULL)
    private List<@DtoTag(groups = {SaveDto.class, PatchDto.class}) TagDto> tags;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(Long id, String name, String description, Integer price, Integer duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate, List<@DtoTag(groups =
            {SaveDto.class, PatchDto.class}) TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
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

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        GiftCertificateDto other = (GiftCertificateDto) o;

        if (!id.equals(other.id)) return false;
        if (!name.equals(other.name)) return false;
        if (!description.equals(other.description)) return false;
        if (!price.equals(other.price)) return false;
        if (!duration.equals(other.duration)) return false;
        if (!createDate.equals(other.createDate)) return false;
        if (!lastUpdateDate.equals(other.lastUpdateDate)) return false;
        return tags.equals(other.tags);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        result = 31 * result + tags.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}
