package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.RepositoryException;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.persistence.repository.test.matchers.SameGiftCertificate.sameGiftCertificate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@ActiveProfiles("dev")
class GiftCertificateRepositoryImplTest {
    private static final List<Tag> IN_DB_TAGS = Arrays.asList(
            new Tag(1L, "Tag 1"),
            new Tag(2L, "Tag 2"),
            new Tag(3L, "Tag 3")
    );

    private static final Set<Tag> CERTIFICATE_1_TAGS = new HashSet<>(IN_DB_TAGS.subList(0, 2));
    private static GiftCertificate testCertificate = new GiftCertificate(3L, "test", "test", 1, 1,
            LocalDateTime.parse("2021-09-28T18:13:56"), LocalDateTime.parse("2021-09-28T18:13:56"), CERTIFICATE_1_TAGS);
    private static final Set<Tag> CERTIFICATE_2_TAGS = new HashSet<>(IN_DB_TAGS.subList(1, 3));
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
        Page<GiftCertificate> certificates = repository.findAll(PageRequest.of(0, 10));
        assertThat(certificates.getContent(),
                containsInAnyOrder(
                        sameGiftCertificate(IN_DB_CERTIFICATES.get(0)),
                        sameGiftCertificate(IN_DB_CERTIFICATES.get(1))
                )
        );
    }

    @Test
    void findById() {
        GiftCertificate certificate = repository.findById(2L).orElse(null);
        assertThat(certificate, sameGiftCertificate(IN_DB_CERTIFICATES.get(1)));
    }

    @Test
    void findNonExistingCertificateById() {
        GiftCertificate certificate = repository.findById(100L).orElse(null);
        assertThat(certificate, is(nullValue()));
    }

    @Test
    void findWithFilters() {
        GiftCertificatesFilterConfig config = GiftCertificatesFilterConfig.builder().withSearchPattern("2").build();
        List<GiftCertificate> certificate = repository.findWithFilters(config, 0, 10).getContent();
        assertThat(certificate, containsInAnyOrder(sameGiftCertificate(IN_DB_CERTIFICATES.get(1))));
    }

    @Test
    void findWithFiltersNoMatchingCertificates() {
        GiftCertificatesFilterConfig config =
                GiftCertificatesFilterConfig.builder().withTags(Collections.singletonList("Tag 123")).build();
        List<GiftCertificate> certificates = repository.findWithFilters(config, 1, 10).getContent();
        assertThat(certificates, is(empty()));
    }

    @Test
    void update() {
        testCertificate.setId(1L);
        GiftCertificate updatedCertificate = repository.save(testCertificate);
        assertThat(updatedCertificate, is(sameGiftCertificate(testCertificate)));
    }

    @Test
    void deleteExistingBoundCertificate() {
        GiftCertificate certificate = repository.getById(1L);
        repository.delete(certificate);
        assertThat(repository.findById(1L).orElse(null), is(nullValue()));
    }

    @Test
    void deleteExistingUnboundCertificate() {
        GiftCertificate saved = repository.save(testCertificate);
        repository.delete(saved);
        assertThat(repository.findById(saved.getId()).orElse(null), is(nullValue()));
    }

    @Test
    void deleteNonExistingCertificate() {
        repository.delete(testCertificate);
        assertThat(repository.findById(testCertificate.getId()).orElse(null), is(nullValue()));
    }
}