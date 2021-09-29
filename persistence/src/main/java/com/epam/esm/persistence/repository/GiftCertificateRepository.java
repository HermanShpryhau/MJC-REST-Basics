package com.epam.esm.persistence.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {
    List<GiftCertificate> findByPatternInNameOrDescription(String pattern);

    List<GiftCertificate> findByTagName(String tagName);

    List<Tag> findAssociatedTags(Long certificateId);

    void addTagAssociation(Long certificateId, Long tagId);

    void removeTagAssociation(Long certificateId, Long tagId);
}
