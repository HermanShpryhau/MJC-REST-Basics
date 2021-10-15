package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;

import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final Tag[] TEST_TAGS = {
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2"),
            new Tag(3L, "Tag 3")
    };

    private static final List<Tag> CERTIFICATE_1_TAGS = Arrays.asList(TEST_TAGS[0], TEST_TAGS[1]);
    private static final List<Tag> CERTIFICATE_2_TAGS = Arrays.asList(TEST_TAGS[1], TEST_TAGS[2]);

    private static final GiftCertificate[] TEST_CERTIFICATES = {
            new GiftCertificate(1L, "Tag 1", "Description 1", 1, 1,
                    Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now())),
            new GiftCertificate(2L, "Tag 2", "Description 2", 2, 2,
                    Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()))
    };

    private static final GiftCertificateDto[] TEST_CERTIFICATE_DTOS = {
            new GiftCertificateDto(TEST_CERTIFICATES[0].getId(), TEST_CERTIFICATES[0].getName(),
                    TEST_CERTIFICATES[0].getDescription(), TEST_CERTIFICATES[0].getPrice(),
                    TEST_CERTIFICATES[0].getDuration(), TEST_CERTIFICATES[0].getCreateDate().toLocalDateTime(),
                    TEST_CERTIFICATES[0].getLastUpdateDate().toLocalDateTime(), CERTIFICATE_1_TAGS),
            new GiftCertificateDto(TEST_CERTIFICATES[1].getId(), TEST_CERTIFICATES[1].getName(),
                    TEST_CERTIFICATES[1].getDescription(), TEST_CERTIFICATES[1].getPrice(),
                    TEST_CERTIFICATES[1].getDuration(), TEST_CERTIFICATES[1].getCreateDate().toLocalDateTime(),
                    TEST_CERTIFICATES[1].getLastUpdateDate().toLocalDateTime(), CERTIFICATE_2_TAGS)
    };

    @Mock
    private GiftCertificateRepository mockCertificateRepository;

    @Mock
    private TagRepository mockTagRepository;

    @InjectMocks
    private GiftCertificateDtoTranslatorImpl translator;

    private GiftCertificateService service;

    @BeforeEach
    void setUp() {
        service = new GiftCertificateServiceImpl(mockCertificateRepository, mockTagRepository, translator);
    }

    @Test
    void addCertificateTest() {
        Mockito.when(mockCertificateRepository.save(TEST_CERTIFICATES[0])).thenReturn(TEST_CERTIFICATES[0]);
        Mockito.when(mockTagRepository.findByName(TEST_TAGS[0].getName())).thenReturn(TEST_TAGS[0]);
        Mockito.when(mockTagRepository.findByName(TEST_TAGS[1].getName())).thenReturn(TEST_TAGS[1]);

        GiftCertificateDto savedCertificate = service.addCertificate(TEST_CERTIFICATE_DTOS[0]);
        savedCertificate.setTags(savedCertificate.getTags().stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toList()));
        Assertions.assertEquals(TEST_CERTIFICATE_DTOS[0], savedCertificate);
    }

    @Test
    void fetchCertificatesWithFiltersTest() {
        Mockito.when(mockCertificateRepository.findWithFilters(Mockito.any(QueryFiltersConfig.class)))
                .thenReturn(Collections.singletonList(TEST_CERTIFICATES[0]));
        Mockito.when(mockCertificateRepository.findAssociatedTags(TEST_CERTIFICATES[0].getId())).thenReturn(CERTIFICATE_1_TAGS);

        List<GiftCertificateDto> certificate = service.fetchCertificatesWithFilters(Optional.empty(), Optional.empty(), Optional.empty());
        Assertions.assertEquals(Collections.singletonList(TEST_CERTIFICATE_DTOS[0]), certificate);
    }

    @Test
    void fetchCertificateByIdTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(TEST_CERTIFICATES[0]);
        Mockito.when(mockCertificateRepository.findAssociatedTags(TEST_CERTIFICATES[0].getId())).thenReturn(CERTIFICATE_1_TAGS);

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

        List<Tag> result = service.fetchAssociatedTags(1L);
        Assertions.assertEquals(CERTIFICATE_1_TAGS, result);
    }

    @Test
    void fetchAssociatedTagsForNonExistingCertificateTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchAssociatedTags(1L));
    }

    @Test
    void updateCertificateTest() {
        Mockito.when(mockCertificateRepository.findById(1L)).thenReturn(TEST_CERTIFICATES[0]);
        Mockito.when(mockCertificateRepository.update(1L, TEST_CERTIFICATES[0])).thenReturn(TEST_CERTIFICATES[0]);
        Mockito.when(mockTagRepository.findByName(TEST_TAGS[0].getName())).thenReturn(TEST_TAGS[0]);
        Mockito.when(mockTagRepository.findByName(TEST_TAGS[1].getName())).thenReturn(TEST_TAGS[1]);
        Mockito.when(mockCertificateRepository.findAssociatedTags(1L)).thenReturn(CERTIFICATE_1_TAGS);

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