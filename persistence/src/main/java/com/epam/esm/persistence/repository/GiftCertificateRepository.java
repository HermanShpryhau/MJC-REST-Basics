package com.epam.esm.persistence.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {

    /**
     * Finds gift certificates according to filtering parameters supplied with {@link QueryFiltersConfig}
     * @param config Filter configuration
     * @return List of found gift certificates
     */
    List<GiftCertificate> findWithFilters(QueryFiltersConfig config);

    /**
     * Finds tags associated with gift certificate
     * @param certificateId ID of gift certificate
     * @return List of associated tags
     */
    List<Tag> findAssociatedTags(Long certificateId);

    /**
     * Associates gift certificate with tag
     * @param certificateId ID of gift certificate to associate
     * @param tagId ID of tag to associate
     */
    void addTagAssociation(Long certificateId, Long tagId);

    /**
     * Removes association between gift certificate and tag
     * @param certificateId ID of gift certificate to associate
     * @param tagId ID of tag to associate
     */
    void removeTagAssociation(Long certificateId, Long tagId);
}
