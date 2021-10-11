package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPersistenceConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GiftCertificateRepositoryImplTest {
    private static final List<Tag> IN_DB_TAGS = Arrays.asList(
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2"),
            new Tag(3L, "Tag 3")
    );

    private static final List<Tag> CERTIFICATE_1_TAGS = IN_DB_TAGS.subList(0, 2);

    private static final List<GiftCertificate> IN_DB_CERTIFICATES = Arrays.asList(
            new GiftCertificate(1L, "Certificate 1", "Description 1", 119, 25,
                    Timestamp.valueOf(LocalDateTime.parse("2021-09-28T18:13:56")), Timestamp.valueOf(LocalDateTime.parse("2021-09-28T18:13:56"))),
            new GiftCertificate(2L, "Certificate 2", "Description 2", 191, 28,
                    Timestamp.valueOf(LocalDateTime.parse("2021-09-27T18:13:56")), Timestamp.valueOf(LocalDateTime.parse("2021-09-27T18:13:56")))
    );

    private static final GiftCertificate TEST_CERTIFICATE = new GiftCertificate(1L, "test", "test", 12, 12,
            null, null);
    private final GiftCertificateRepository repository;

    @Autowired
    public GiftCertificateRepositoryImplTest(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Test
    @Order(13)
    void save() {
        GiftCertificate saved = repository.save(TEST_CERTIFICATE);
        TEST_CERTIFICATE.setId(saved.getId());
        TEST_CERTIFICATE.setCreateDate(saved.getCreateDate());
        TEST_CERTIFICATE.setLastUpdateDate(saved.getLastUpdateDate());
        Assertions.assertEquals(TEST_CERTIFICATE, saved);
    }

    @Test
    @Order(1)
    void findAll() {
        List<GiftCertificate> certificates = repository.findAll();
        Assertions.assertEquals(IN_DB_CERTIFICATES, certificates);
    }

    @Test
    @Order(2)
    void findById() {
        GiftCertificate certificate = repository.findById(1L);
        Assertions.assertEquals(IN_DB_CERTIFICATES.get(0), certificate);
    }

    @Test
    @Order(3)
    void findNonExistingCertificateById() {
        GiftCertificate certificate = repository.findById(100L);
        Assertions.assertNull(certificate);
    }

    @Test
    @Order(4)
    void findWithFilters() {
        QueryFiltersConfig config = QueryFiltersConfig.builder().withSearchPattern("Certificate 1").build();
        GiftCertificate certificate = repository.findWithFilters(config).get(0);
        Assertions.assertEquals(IN_DB_CERTIFICATES.get(0), certificate);
    }

    @Test
    @Order(5)
    void findWithFiltersNoMatchingCertificates() {
        QueryFiltersConfig config = QueryFiltersConfig.builder().withTag("Tag 123").build();
        List<GiftCertificate> certificates = repository.findWithFilters(config);
        Assertions.assertEquals(Collections.emptyList(), certificates);
    }

    @Test
    @Order(6)
    void findAssociatedTags() {
        List<Tag> tags = repository.findAssociatedTags(1L);
        Assertions.assertEquals(CERTIFICATE_1_TAGS, tags);
    }

    @Test
    @Order(7)
    void findAssociatedTagsForNonExistingCertificate() {
        List<Tag> tags = repository.findAssociatedTags(123L);
        Assertions.assertEquals(Collections.emptyList(), tags);
    }

    @Test
    @Order(8)
    void update() {
        GiftCertificate updatedCertificate = repository.update(1L, TEST_CERTIFICATE);
        Assertions.assertEquals(TEST_CERTIFICATE, updatedCertificate);
    }

    @Test
    @Order(11)
    void deleteExistingCertificate() {
        Assertions.assertTrue(repository.delete(1L));
    }

    @Test
    @Order(12)
    void deleteNonExistingCertificate() {
        Assertions.assertFalse(repository.delete(123L));
    }

    @Test
    @Order(9)
    void addTagAssociation() {
        repository.addTagAssociation(1L, IN_DB_TAGS.get(2).getId());
        Assertions.assertEquals(IN_DB_TAGS, repository.findAssociatedTags(1L));
    }

    @Test
    @Order(10)
    void removeTagAssociation() {
        repository.removeTagAssociation(1L, IN_DB_TAGS.get(2).getId());
        Assertions.assertEquals(CERTIFICATE_1_TAGS, repository.findAssociatedTags(1L));
    }
}