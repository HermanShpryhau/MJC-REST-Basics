package com.epam.esm.model.dto;

import com.epam.esm.model.validation.DtoTag;
import com.epam.esm.model.validation.PatchDto;
import com.epam.esm.model.validation.SaveDto;
import com.epam.esm.model.validation.ValidationErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto implements DataTransferObject {
    @NotNull(groups = PatchDto.class, message = ValidationErrorCode.PATCH_CERTIFICATE_ID_NOT_NULL)
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
}
