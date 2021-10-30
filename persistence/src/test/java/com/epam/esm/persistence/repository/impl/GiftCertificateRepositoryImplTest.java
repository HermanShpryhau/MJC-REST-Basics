package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
@ComponentScan(basePackages = "com.epam.esm")
class GiftCertificateRepositoryImplTest {
    private static final List<Tag> IN_DB_TAGS = Arrays.asList(
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2"),
            new Tag(3L, "Tag 3")
    );

    private static final List<Tag> CERTIFICATE_2_TAGS = IN_DB_TAGS.subList(1, 3);

    private static final List<GiftCertificate> IN_DB_CERTIFICATES = Arrays.asList(
            new GiftCertificate(),
            new GiftCertificate()
    );

    private static final GiftCertificate TEST_CERTIFICATE = new GiftCertificate();
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
        GiftCertificatesFilterConfig config = GiftCertificatesFilterConfig.builder().withSearchPattern("2").build();
        GiftCertificate certificate = repository.findWithFilters(config, 1, 10).get(0);
        Assertions.assertEquals(IN_DB_CERTIFICATES.get(1), certificate);
    }

    @Test
    void findWithFiltersNoMatchingCertificates() {
        GiftCertificatesFilterConfig config =
                GiftCertificatesFilterConfig.builder().withTags(Collections.singletonList("Tag 123")).build();
        List<GiftCertificate> certificates = repository.findWithFilters(config, 1, 10);
        Assertions.assertEquals(Collections.emptyList(), certificates);
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