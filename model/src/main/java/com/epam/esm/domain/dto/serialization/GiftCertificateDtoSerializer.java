package com.epam.esm.domain.dto.serialization;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@Qualifier("giftCertificateDtoSerializer")
public class GiftCertificateDtoSerializer implements DtoSerializer<GiftCertificateDto, GiftCertificate> {

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
        dto.setTags(new ArrayList<>(certificate.getAssociatedTags()));
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
        certificate.setAssociatedTags(dto.getTags());
        return certificate;
    }
}
