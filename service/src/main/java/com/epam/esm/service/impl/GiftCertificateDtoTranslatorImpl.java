package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateDtoTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GiftCertificateDtoTranslatorImpl implements GiftCertificateDtoTranslator {
    private final GiftCertificateRepository certificateRepository;

    @Autowired
    public GiftCertificateDtoTranslatorImpl(GiftCertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public GiftCertificateDto giftCertificateToDto(GiftCertificate certificate, List<Tag> tags) {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(certificate.getDuration());
        dto.setCreateDate(certificate.getCreateDate().toLocalDateTime());
        dto.setLastUpdateDate(certificate.getLastUpdateDate().toLocalDateTime());
        dto.setTags(tags);
        return dto;
    }

    @Override
    public GiftCertificateDto giftCertificateToDto(GiftCertificate certificate) {
        List<Tag> tags = certificateRepository.findAssociatedTags(certificate.getId());
        return giftCertificateToDto(certificate, tags);
    }

    @Override
    public GiftCertificate dtoToGiftCertificate(GiftCertificateDto dto) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());
        certificate.setCreateDate(Timestamp.valueOf(dto.getCreateDate()));
        certificate.setLastUpdateDate(Timestamp.valueOf(dto.getLastUpdateDate()));
        return certificate;
    }
}
