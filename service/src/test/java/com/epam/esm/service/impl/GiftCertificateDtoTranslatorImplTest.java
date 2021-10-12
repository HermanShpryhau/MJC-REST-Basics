package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GiftCertificateDtoTranslatorImplTest {

    private static final List<Tag> CERTIFICATE_1_TAGS = Arrays.asList(
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2")
    );

    private static final GiftCertificate TEST_CERTIFICATE = new GiftCertificate(1L, "Tag 1", "Description 1", 1, 1,
            Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));

    private static final GiftCertificateDto TEST_CERTIFICATE_DTO =
            new GiftCertificateDto(TEST_CERTIFICATE.getId(), TEST_CERTIFICATE.getName(),
                    TEST_CERTIFICATE.getDescription(), TEST_CERTIFICATE.getPrice(),
                    TEST_CERTIFICATE.getDuration(), TEST_CERTIFICATE.getCreateDate().toLocalDateTime(),
                    TEST_CERTIFICATE.getLastUpdateDate().toLocalDateTime(), CERTIFICATE_1_TAGS);

    @Mock
    private GiftCertificateRepository mockRepository;

    @InjectMocks
    private GiftCertificateDtoTranslatorImpl translator;

    @Test
    void giftCertificateToDtoTest() {
        Mockito.when(mockRepository.findAssociatedTags(TEST_CERTIFICATE.getId())).thenReturn(CERTIFICATE_1_TAGS);

        GiftCertificateDto result = translator.giftCertificateToDto(TEST_CERTIFICATE);
        Assertions.assertEquals(TEST_CERTIFICATE_DTO, result);
    }

    @Test
    void dtoToGiftCertificate() {
        GiftCertificate result = translator.dtoToGiftCertificate(TEST_CERTIFICATE_DTO);
        Assertions.assertEquals(TEST_CERTIFICATE, result);
    }
}