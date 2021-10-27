package com.epam.esm.service.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.serialization.GiftCertificateDtoSerializer;
import com.epam.esm.model.dto.serialization.TagDtoSerializer;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final Tag[] TEST_TAGS = {
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2"),
            new Tag(3L, "Tag 3")
    };

    private static final TagDto[] TEST_TAG_DTOS = {
            new TagDto(1L, "Tag 1"),
            new TagDto(2L, "Tag 2"),
            new TagDto(3L, "Tag 3")
    };

    private static final List<Tag> CERTIFICATE_1_TAGS = Arrays.asList(TEST_TAGS[0], TEST_TAGS[1]);
    private static final List<Tag> CERTIFICATE_2_TAGS = Arrays.asList(TEST_TAGS[1], TEST_TAGS[2]);

    private static final List<TagDto> CERTIFICATE_1_TAG_DTOS = Arrays.asList(TEST_TAG_DTOS[0], TEST_TAG_DTOS[1]);
    private static final List<TagDto> CERTIFICATE_2_TAG_DTOS = Arrays.asList(TEST_TAG_DTOS[1], TEST_TAG_DTOS[2]);

    private static final GiftCertificate[] TEST_CERTIFICATES = {
            new GiftCertificate(1L, "Certificate 1", "Description 1", 1, 1,
                    LocalDateTime.now(), LocalDateTime.now(), CERTIFICATE_1_TAGS),
            new GiftCertificate(2L, "Certificate 2", "Description 2", 2, 2,
                    LocalDateTime.now(), LocalDateTime.now(), CERTIFICATE_2_TAGS)
    };

    private static final GiftCertificateDto[] TEST_CERTIFICATE_DTOS = {
            new GiftCertificateDto(TEST_CERTIFICATES[0].getId(), TEST_CERTIFICATES[0].getName(),
                    TEST_CERTIFICATES[0].getDescription(), TEST_CERTIFICATES[0].getPrice(),
                    TEST_CERTIFICATES[0].getDuration(), TEST_CERTIFICATES[0].getCreateDate(),
                    TEST_CERTIFICATES[0].getLastUpdateDate(), CERTIFICATE_1_TAG_DTOS),
            new GiftCertificateDto(TEST_CERTIFICATES[1].getId(), TEST_CERTIFICATES[1].getName(),
                    TEST_CERTIFICATES[1].getDescription(), TEST_CERTIFICATES[1].getPrice(),
                    TEST_CERTIFICATES[1].getDuration(), TEST_CERTIFICATES[1].getCreateDate(),
                    TEST_CERTIFICATES[1].getLastUpdateDate(), CERTIFICATE_2_TAG_DTOS)
    };

    @Mock
    private GiftCertificateRepository mockCertificateRepository;

    @Mock
    private TagRepository mockTagRepository;

    private final TagDtoSerializer tagDtoSerializer = new TagDtoSerializer();

    private final GiftCertificateDtoSerializer certificateDtoSerializer = new GiftCertificateDtoSerializer(tagDtoSerializer);

    private GiftCertificateService service;

    @BeforeEach
    void setUp() {
        service = new GiftCertificateServiceImpl(mockCertificateRepository, mockTagRepository, certificateDtoSerializer,
                tagDtoSerializer);
    }

    @Test
    void addCertificateTest() {
        GiftCertificate entityCopy = new GiftCertificate(1L, "Certificate 1", "Description 1", 1, 1,
                TEST_CERTIFICATE_DTOS[0].getCreateDate(), TEST_CERTIFICATE_DTOS[0].getLastUpdateDate(), CERTIFICATE_1_TAGS);
        GiftCertificateDto dtoCopy = new GiftCertificateDto(1L, "Certificate 1", "Description 1", 1, 1,
                TEST_CERTIFICATE_DTOS[0].getCreateDate(), TEST_CERTIFICATE_DTOS[0].getLastUpdateDate(), CERTIFICATE_1_TAG_DTOS);
        Mockito.when(mockCertificateRepository.save(Mockito.any(GiftCertificate.class))).thenReturn(entityCopy);
        Mockito.when(mockTagRepository.findByName(TEST_TAGS[0].getName())).thenReturn(TEST_TAGS[0]);
        Mockito.when(mockTagRepository.findByName(TEST_TAGS[1].getName())).thenReturn(TEST_TAGS[1]);

        GiftCertificateDto savedCertificate = service.addCertificate(dtoCopy);
        Assertions.assertEquals(TEST_CERTIFICATE_DTOS[0], savedCertificate);
    }

    @Test
    void fetchCertificatesWithFiltersTest() {
        Mockito.when(mockCertificateRepository.findWithFilters(Mockito.any(GiftCertificatesFilterConfig.class),
                        Mockito.eq(1), Mockito.eq(10)))
                .thenReturn(Collections.singletonList(TEST_CERTIFICATES[0]));

        List<GiftCertificateDto> certificate = service.fetchCertificatesWithFilters(Optional.empty(),
                Optional.empty(), Optional.empty(), 1, 10);
        Assertions.assertEquals(Collections.singletonList(TEST_CERTIFICATE_DTOS[0]), certificate);
    }

    @Test
    void fetchCertificateByIdTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(TEST_CERTIFICATES[0]);

        GiftCertificateDto result = service.fetchCertificateById(1L);
        Assertions.assertEquals(TEST_CERTIFICATE_DTOS[0], result);
    }

    @Test
    void fetchNonExistingCertificateByIdTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchCertificateById(1L));
    }

    @Test
    void fetchAssociatedTagsTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(TEST_CERTIFICATES[0]);
        Mockito.when(mockCertificateRepository.findAssociatedTags(1L)).thenReturn(CERTIFICATE_1_TAGS);

        List<TagDto> result = service.fetchAssociatedTags(1L, 1, 10);
        Assertions.assertEquals(CERTIFICATE_1_TAG_DTOS, result);
    }

    @Test
    void fetchAssociatedTagsForNonExistingCertificateTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchAssociatedTags(1L, 1, 10));
    }

    @Test
    void updateCertificateTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(TEST_CERTIFICATES[0]);
        Mockito.when(mockCertificateRepository.update(TEST_CERTIFICATES[0])).thenReturn(TEST_CERTIFICATES[0]);

        GiftCertificateDto updatedCertificate = service.updateCertificate(TEST_CERTIFICATE_DTOS[0]);
        Assertions.assertEquals(TEST_CERTIFICATE_DTOS[0], updatedCertificate);
    }

    @Test
    void updateNonExistingCertificateTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.updateCertificate(TEST_CERTIFICATE_DTOS[0]));
    }

    @Test
    void deleteNonExistingCertificateTest() {
        Mockito.when(mockCertificateRepository.delete(1L)).thenReturn(false);

        Assertions.assertThrows(ServiceException.class, () -> service.deleteCertificate(1L));
    }
}