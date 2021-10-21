package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GiftCertificateRepositoryImplTest {
    private static final List<Tag> IN_DB_TAGS = Arrays.asList(
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2"),
            new Tag(3L, "Tag 3")
    );

    private static final List<Tag> CERTIFICATE_2_TAGS = IN_DB_TAGS.subList(1, 3);

    private static final List<GiftCertificate> IN_DB_CERTIFICATES = Arrays.asList(
            new GiftCertificate(1L, "Certificate 1", "Description 1", 119, 25,
                    LocalDateTime.parse("2021-09-28T18:13:56"),
                    LocalDateTime.parse("2021-09-28T18:13:56")),
            new GiftCertificate(2L, "Certificate 2", "Description 2", 191, 28,
                    LocalDateTime.parse("2021-09-27T18:13:56"),
                    LocalDateTime.parse("2021-09-27T18:13:56"))
    );

    private static final GiftCertificate TEST_CERTIFICATE = new GiftCertificate(0L, "test", "test", 12, 12,
            null, null);
    private final GiftCertificateRepository repository;

    @Autowired
    public GiftCertificateRepositoryImplTest(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Test
    void save() {
        GiftCertificate saved = repository.save(TEST_CERTIFICATE);
        TEST_CERTIFICATE.setId(saved.getId());
        TEST_CERTIFICATE.setCreateDate(saved.getCreateDate());
        TEST_CERTIFICATE.setLastUpdateDate(saved.getLastUpdateDate());
        Assertions.assertEquals(TEST_CERTIFICATE, saved);
    }

    @Test
    void findAll() {
        List<GiftCertificate> certificates = repository.findAll(1, 10);
        Assertions.assertEquals(IN_DB_CERTIFICATES, certificates);
    }

    @Test
    void findById() {
        GiftCertificate certificate = repository.findById(2L);
        Assertions.assertEquals(IN_DB_CERTIFICATES.get(1), certificate);
    }

    @Test
    void findNonExistingCertificateById() {
        GiftCertificate certificate = repository.findById(100L);
        Assertions.assertNull(certificate);
    }

    @Test
    void findWithFilters() {
        QueryFiltersConfig config = QueryFiltersConfig.builder().withSearchPattern("2").build();
        GiftCertificate certificate = repository.findWithFilters(config, 1, 10).get(0);
        Assertions.assertEquals(IN_DB_CERTIFICATES.get(1), certificate);
    }

    @Test
    void findWithFiltersNoMatchingCertificates() {
        QueryFiltersConfig config = QueryFiltersConfig.builder().withTags(Collections.singletonList("Tag 123")).build();
        List<GiftCertificate> certificates = repository.findWithFilters(config, 1, 10);
        Assertions.assertEquals(Collections.emptyList(), certificates);
    }

    @Test
    void findAssociatedTags() {
        List<Tag> tags = repository.findAssociatedTags(2L);
        Assertions.assertEquals(CERTIFICATE_2_TAGS, tags);
    }

    @Test
    void findAssociatedTagsForNonExistingCertificate() {
        List<Tag> tags = repository.findAssociatedTags(123L);
        Assertions.assertEquals(Collections.emptyList(), tags);
    }

    @Test
    void update() {
        GiftCertificate updatedCertificate = repository.update(TEST_CERTIFICATE);
        Assertions.assertEquals(TEST_CERTIFICATE, updatedCertificate);
    }

    @Test
    void deleteExistingCertificate() {
        Assertions.assertTrue(repository.delete(1L));
    }

    @Test
    void deleteNonExistingCertificate() {
        Assertions.assertFalse(repository.delete(123L));
    }
}