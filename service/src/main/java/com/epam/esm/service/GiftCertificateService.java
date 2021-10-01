package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto addCertificate(GiftCertificateDto dto);

    List<GiftCertificateDto> fetchAllCertificates();

    GiftCertificateDto fetchCertificateById(Long id);

    List<GiftCertificateDto> searchByPatternInNameOrDescription(String pattern);

    List<GiftCertificateDto> fetchCertificatesWithTag(String tagName);

    List<Tag> fetchAssociatedTags(long certificateId);

    GiftCertificateDto updateCertificate(GiftCertificateDto dto);

    void deleteCertificate(long id);
}
