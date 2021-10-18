package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateDtoTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateDtoTranslatorImpl implements GiftCertificateDtoTranslator {

    @Override
    public GiftCertificateDto giftCertificateToDto(GiftCertificate certificate) {
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
    public GiftCertificate dtoToGiftCertificate(GiftCertificateDto dto) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());
        Optional.ofNullable(dto.getCreateDate()).ifPresent(certificate::setCreateDate);
        Optional.ofNullable(dto.getLastUpdateDate()).ifPresent(certificate::setLastUpdateDate);
        certificate.setAssociatedTags(new HashSet<>(dto.getTags()));
        return certificate;
    }
}
