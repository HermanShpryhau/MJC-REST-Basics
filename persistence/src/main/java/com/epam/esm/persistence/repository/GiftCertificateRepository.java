package com.epam.esm.persistence.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {

    /**
     * Finds gift certificates according to filtering parameters supplied with {@link QueryFiltersConfig}
     *
     * @param config Filter configuration
     * @return List of found gift certificates
     */
    List<GiftCertificate> findWithFilters(QueryFiltersConfig config, int page, int count);

    /**
     * Finds tags associated with gift certificate
     *
     * @param certificateId ID of gift certificate
     * @return List of associated tags
     */
    List<Tag> findAssociatedTags(Long certificateId);
}
