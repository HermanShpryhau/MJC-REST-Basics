package com.epam.esm.persistence.repository;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {

    /**
     * Finds gift certificates according to filtering parameters supplied with {@link GiftCertificatesFilterConfig}.
     *
     * @param config Filter configuration
     * @param page Index of page
     * @param size Size of page
     * @return List of found gift certificates
     */
    List<GiftCertificate> findWithFilters(GiftCertificatesFilterConfig config, int page, int size);

    /**
     * Finds tags associated with gift certificate.
     *
     * @param certificateId ID of gift certificate
     * @return List of associated tags
     */
    List<Tag> findAssociatedTags(Long certificateId);
}
