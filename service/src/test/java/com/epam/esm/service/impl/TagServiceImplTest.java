package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.GiftCertificateDtoTranslator;
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
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    private static final Tag[] TEST_TAGS = {
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2"),
            new Tag(3L, "Tag 3")
    };

    private static final List<Tag> CERTIFICATE_1_TAGS = Arrays.asList(TEST_TAGS[0], TEST_TAGS[1]);

    private static final GiftCertificate TEST_CERTIFICATE =
            new GiftCertificate(1L, "Tag 1", "Description 1", 1, 1,
                    Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));

    private static final GiftCertificateDto TEST_CERTIFICATE_DTO =
            new GiftCertificateDto(TEST_CERTIFICATE.getId(), TEST_CERTIFICATE.getName(),
                    TEST_CERTIFICATE.getDescription(), TEST_CERTIFICATE.getPrice(),
                    TEST_CERTIFICATE.getDuration(), TEST_CERTIFICATE.getCreateDate().toLocalDateTime(),
                    TEST_CERTIFICATE.getLastUpdateDate().toLocalDateTime(), CERTIFICATE_1_TAGS);

    @Mock
    private TagRepository mockTagRepository;

    @Mock
    private GiftCertificateDtoTranslator translator;

    @InjectMocks
    private TagServiceImpl service;

    @Test
    void fetchAllTagsTest() {
        Mockito.when(mockTagRepository.findAll()).thenReturn(Arrays.asList(TEST_TAGS));

        List<Tag> result = service.fetchAllTags();
        Assertions.assertEquals(Arrays.asList(TEST_TAGS), result);
    }

    @Test
    void fetchEmptyListIfNoTagsInDBTest() {
        Mockito.when(mockTagRepository.findAll()).thenReturn(Collections.emptyList());

        List<Tag> result = service.fetchAllTags();
        Assertions.assertEquals(Collections.emptyList(), result);
    }

    @Test
    void fetchTagByIdTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(TEST_TAGS[0]);

        Tag result = service.fetchTagById(1L);
        Assertions.assertEquals(TEST_TAGS[0], result);
    }

    @Test
    void fetchNonExistingTagByIdTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchTagById(1L));
    }

    @Test
    void fetchTagByNameTest() {
        Mockito.when(mockTagRepository.findByName("Tag 1")).thenReturn(TEST_TAGS[0]);

        Tag result = service.fetchTagByName("Tag 1");
        Assertions.assertEquals(TEST_TAGS[0], result);
    }

    @Test
    void fetchNonExistingTagByNameTest() {
        Mockito.when(mockTagRepository.findByName("Tag 1")).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchTagByName("Tag 1"));
    }

    @Test
    void fetchAssociatedCertificatesTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(TEST_TAGS[0]);
        Mockito.when(mockTagRepository.findAssociatedGiftCertificates(1L)).thenReturn(Collections.singletonList(TEST_CERTIFICATE));
        Mockito.when(translator.giftCertificateToDto(TEST_CERTIFICATE)).thenReturn(TEST_CERTIFICATE_DTO);

        List<GiftCertificateDto> result = service.fetchAssociatedCertificates(1L);
        Assertions.assertEquals(Collections.singletonList(TEST_CERTIFICATE_DTO), result);
    }

    @Test
    void fetchNonExistingTagAssociatedCertificatesTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchAssociatedCertificates(1L));
    }

    @Test
    void deleteNonExistingTag() {
        Assertions.assertThrows(ServiceException.class, () -> service.deleteTag(1L));
    }
}