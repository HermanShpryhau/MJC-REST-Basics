package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateDtoTranslator {
    /**
     * Translates gift certificate entity to gift certificate DTO.
     *
     * @param certificate Gift certificate entity
     * @param tags        List of associated tags
     * @return Gift certificate DTO
     */
    GiftCertificateDto giftCertificateToDto(GiftCertificate certificate, List<Tag> tags);

    /**
     * Translates gift certificate entity to gift certificate DTO. Associated tags are fetched from data source.
     *
     * @param certificate Gift certificate entity
     * @return Gift certificate DTO
     */
    GiftCertificateDto giftCertificateToDto(GiftCertificate certificate);

    /**
     * Transalte gift certificate DTO to gift certificate entity
     *
     * @param dto Gift certificate DTO
     * @return Gift certificate entity object
     */
    GiftCertificate dtoToGiftCertificate(GiftCertificateDto dto);
}
