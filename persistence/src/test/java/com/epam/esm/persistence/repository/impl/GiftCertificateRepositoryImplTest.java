package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.RepositoryException;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.persistence.repository.test.matchers.SameGiftCertificate.sameGiftCertificate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

    private static final List<Tag> CERTIFICATE_1_TAGS = IN_DB_TAGS.subList(0, 2);
    private static GiftCertificate testCertificate = new GiftCertificate(3L, "test", "test", 1, 1,
            LocalDateTime.parse("2021-09-28T18:13:56"), LocalDateTime.parse("2021-09-28T18:13:56"), CERTIFICATE_1_TAGS);
    private static final List<Tag> CERTIFICATE_2_TAGS = IN_DB_TAGS.subList(1, 3);
    private static final List<GiftCertificate> IN_DB_CERTIFICATES = Arrays.asList(
            new GiftCertificate(1L, "Certificate 1", "Description 1", 119, 25,
                    LocalDateTime.parse("2021-09-28T18:13:56"), LocalDateTime.parse("2021-09-28T18:13:56"),
                    CERTIFICATE_1_TAGS),
            new GiftCertificate(2L, "Certificate 2", "Description 2", 191, 28,
                    LocalDateTime.parse("2021-09-27T18:13:56"), LocalDateTime.parse("2021-09-27T18:13:56"),
                    CERTIFICATE_2_TAGS)
    );
    private final GiftCertificateRepository repository;

    @Autowired
    public GiftCertificateRepositoryImplTest(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Test
    void save() {
        GiftCertificate saved = repository.save(testCertificate);
        testCertificate.setId(saved.getId());
        testCertificate.setCreateDate(saved.getCreateDate());
        testCertificate.setLastUpdateDate(saved.getLastUpdateDate());
        assertThat(saved, is(sameGiftCertificate(testCertificate)));
    }

    @Test
    void findAll() {
        List<GiftCertificate> certificates = repository.findAll(1, 10);
        assertThat(certificates,
                containsInAnyOrder(
                        sameGiftCertificate(IN_DB_CERTIFICATES.get(0)),
                        sameGiftCertificate(IN_DB_CERTIFICATES.get(1))
                )
        );
    }

    @Test
    void findById() {
        GiftCertificate certificate = repository.findById(2L);
        assertThat(certificate, sameGiftCertificate(IN_DB_CERTIFICATES.get(1)));
    }

    @Test
    void findNonExistingCertificateById() {
        GiftCertificate certificate = repository.findById(100L);
        assertThat(certificate, is(nullValue()));
    }

    @Test
    void findWithFilters() {
        GiftCertificatesFilterConfig config = GiftCertificatesFilterConfig.builder().withSearchPattern("2").build();
        List<GiftCertificate> certificate = repository.findWithFilters(config, 1, 10);
        assertThat(certificate, containsInAnyOrder(sameGiftCertificate(IN_DB_CERTIFICATES.get(1))));
    }

    @Test
    void findWithFiltersNoMatchingCertificates() {
        GiftCertificatesFilterConfig config =
                GiftCertificatesFilterConfig.builder().withTags(Collections.singletonList("Tag 123")).build();
        List<GiftCertificate> certificates = repository.findWithFilters(config, 1, 10);
        assertThat(certificates, is(empty()));
    }

    @Test
    void update() {
        testCertificate.setId(1L);
        GiftCertificate updatedCertificate = repository.update(testCertificate);
        assertThat(updatedCertificate, is(sameGiftCertificate(testCertificate)));
    }

    @Test
    void deleteExistingBoundCertificate() {
        Assertions.assertThrows(RepositoryException.class, () -> repository.delete(1L));
    }

    @Test
    void deleteExistingUnboundCertificate() {
        GiftCertificate saved = repository.save(testCertificate);
        boolean isDeleted = repository.delete(saved.getId());
        assertThat(isDeleted, is(true));
        assertThat(repository.findById(saved.getId()), is(nullValue()));
    }

    @Test
    void deleteNonExistingCertificate() {
        boolean isDeleted = repository.delete(123L);
        assertThat(isDeleted, is(false));
    }
}