package com.epam.esm.domain.dto;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.validation.DtoTag;
import com.epam.esm.domain.validation.PatchDto;
import com.epam.esm.domain.validation.SaveDto;
import com.epam.esm.domain.validation.ValidationErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateDto {
    private Long id;

    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_NAME_NOT_NULL)
    @Size(min = 1, max = 45, groups = {SaveDto.class, PatchDto.class}, message = ValidationErrorCode.INVALID_CERTIFICATE_NAME_LENGTH)
    private String name;

    @NotNull(groups = SaveDto.class, message = ValidationErrorCode.CERTIFICATE_DESCRIPTION_NOT_NULL)
    @Size(min = 1, max = 300, groups = {SaveDto.class, PatchDto.class}, message = ValidationErrorCode.INVALID_CERTIFICATE_DESCRIPTION_LENGTH)
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
    private List<@DtoTag(groups = {SaveDto.class, PatchDto.class}) Tag> tags;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(Long id, String name, String description, Integer price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<@DtoTag(groups = {SaveDto.class, PatchDto.class}) Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getDuration() {
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
