package com.epam.esm.persistence.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {
    List<GiftCertificate> findWithFilters(QueryFiltersConfig config);

    List<Tag> findAssociatedTags(Long certificateId);

    void addTagAssociation(Long certificateId, Long tagId);

    void removeTagAssociation(Long certificateId, Long tagId);
}
