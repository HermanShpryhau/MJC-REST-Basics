package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateDtoTranslator {
    GiftCertificateDto giftCertificateToDto(GiftCertificate certificate, List<Tag> tags);

    GiftCertificate dtoToGiftCertificate(GiftCertificateDto dto);
}
