package com.epam.esm.service.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.serialization.GiftCertificateDtoSerializer;
import com.epam.esm.model.dto.serialization.TagDtoSerializer;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private static final TagDto[] TEST_TAG_DTOS = {
            new TagDto(1L, "Tag 1"),
            new TagDto(2L, "Tag 2"),
            new TagDto(3L, "Tag 3")
    };

    private static final List<Tag> CERTIFICATE_1_TAGS = Arrays.asList(TEST_TAGS[0], TEST_TAGS[1]);
    private static final List<TagDto> CERTIFICATE_1_TAG_DTOS = Arrays.asList(TEST_TAG_DTOS[0], TEST_TAG_DTOS[1]);

    private static final GiftCertificate TEST_CERTIFICATE =
            new GiftCertificate(1L, "Tag 1", "Description 1", 1, 1,
                    LocalDateTime.now(), LocalDateTime.now(), CERTIFICATE_1_TAGS);

    private static final GiftCertificateDto TEST_CERTIFICATE_DTO =
            new GiftCertificateDto(TEST_CERTIFICATE.getId(), TEST_CERTIFICATE.getName(),
                    TEST_CERTIFICATE.getDescription(), TEST_CERTIFICATE.getPrice(),
                    TEST_CERTIFICATE.getDuration(), TEST_CERTIFICATE.getCreateDate(),
                    TEST_CERTIFICATE.getLastUpdateDate(), CERTIFICATE_1_TAG_DTOS);

    @Mock
    private TagRepository mockTagRepository;

    private final TagDtoSerializer tagDtoSerializer = new TagDtoSerializer();

    private final GiftCertificateDtoSerializer certificateDtoSerializer = new GiftCertificateDtoSerializer(tagDtoSerializer);

    private TagService service;

    @BeforeEach
    void setUp() {
        service = new TagServiceImpl(mockTagRepository, tagDtoSerializer, certificateDtoSerializer);
    }

    @Test
    void addTagTest() {
        Mockito.when(mockTagRepository.save(Mockito.any(Tag.class))).thenReturn(TEST_TAGS[0]);

        TagDto savedTag = service.addTag(TEST_TAG_DTOS[0]);
        Assertions.assertEquals(TEST_TAG_DTOS[0], savedTag);
    }

    @Test
    void fetchAllTagsTest() {
        Mockito.when(mockTagRepository.findAll(1, 10)).thenReturn(Arrays.asList(TEST_TAGS));

        List<TagDto> result = service.fetchAllTags(1, 10);
        Assertions.assertEquals(Arrays.asList(TEST_TAG_DTOS), result);
    }

    @Test
    void fetchEmptyListIfNoTagsInDBTest() {
        Mockito.when(mockTagRepository.findAll(1, 10)).thenReturn(Collections.emptyList());

        List<TagDto> result = service.fetchAllTags(1, 10);
        Assertions.assertEquals(Collections.emptyList(), result);
    }

    @Test
    void fetchTagByIdTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(TEST_TAGS[0]);

        TagDto result = service.fetchTagById(1L);
        Assertions.assertEquals(TEST_TAG_DTOS[0], result);
    }

    @Test
    void fetchNonExistingTagByIdTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchTagById(1L));
    }

    @Test
    void fetchAssociatedCertificatesTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(TEST_TAGS[0]);
        Mockito.when(mockTagRepository.findAssociatedGiftCertificates(1L)).thenReturn(Collections.singletonList(TEST_CERTIFICATE));

        List<GiftCertificateDto> result = service.fetchAssociatedCertificates(1L, 1, 10);
        Assertions.assertEquals(Collections.singletonList(TEST_CERTIFICATE_DTO), result);
    }

    @Test
    void fetchNonExistingTagAssociatedCertificatesTest() {
        Mockito.when(mockTagRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(ServiceException.class, () -> service.fetchAssociatedCertificates(1L, 1, 10));
    }

    @Test
    void deleteNonExistingTag() {
        Assertions.assertThrows(ServiceException.class, () -> service.deleteTag(1L));
    }
}