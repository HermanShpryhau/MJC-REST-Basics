package com.epam.esm.model.dto.serialization;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link DtoSerializer} for {@link GiftCertificate} entity.
 */
@Component
@Qualifier("giftCertificateDtoSerializer")
public class GiftCertificateDtoSerializer implements DtoSerializer<GiftCertificateDto, GiftCertificate> {
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer;

    @Autowired
    public GiftCertificateDtoSerializer(@Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer) {
        this.tagDtoSerializer = tagDtoSerializer;
    }

    @Override
    public GiftCertificateDto dtoFromEntity(GiftCertificate certificate) {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(certificate.getDuration());
        Optional.ofNullable(certificate.getCreateDate()).ifPresent(dto::setCreateDate);
        Optional.ofNullable(certificate.getLastUpdateDate()).ifPresent(dto::setLastUpdateDate);
        List<TagDto> tagDtos = new ArrayList<>(
                certificate.getAssociatedTags()).stream()
                .map(tagDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
        dto.setTags(tagDtos);
        return dto;
    }

    @Override
    public GiftCertificate dtoToEntity(GiftCertificateDto dto) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());
        Optional.ofNullable(dto.getCreateDate()).ifPresent(certificate::setCreateDate);
        Optional.ofNullable(dto.getLastUpdateDate()).ifPresent(certificate::setLastUpdateDate);
        certificate.setAssociatedTags(
                dto.getTags().stream().map(tagDtoSerializer::dtoToEntity).collect(Collectors.toList())
        );
        return certificate;
    }
}
