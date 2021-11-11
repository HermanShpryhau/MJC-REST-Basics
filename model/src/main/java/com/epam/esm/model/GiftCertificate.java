package com.epam.esm.model;

import com.epam.esm.model.audit.GiftCertificateAuditingListener;
import com.epam.esm.model.validation.DtoTag;
import com.epam.esm.model.validation.PatchDto;
import com.epam.esm.model.validation.SaveDto;
import com.epam.esm.model.validation.ValidationErrorCode;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Gift_certificate")
//@EntityListeners(GiftCertificateAuditingListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Set<@DtoTag(groups = {SaveDto.class, PatchDto.class}) Tag> associatedTags = new HashSet<>();
}
