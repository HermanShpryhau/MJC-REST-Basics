package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    GiftCertificateDto addCertificate(GiftCertificateDto dto);

    List<GiftCertificateDto> fetchCertificatesWithFilters(Optional<String> tagName,
                                                          Optional<List<String>> sortTypes,
                                                          Optional<String> searchPattern);

    GiftCertificateDto fetchCertificateById(Long id);

    List<Tag> fetchAssociatedTags(long certificateId);

    GiftCertificateDto updateCertificate(GiftCertificateDto dto);

    void deleteCertificate(long id);
}
