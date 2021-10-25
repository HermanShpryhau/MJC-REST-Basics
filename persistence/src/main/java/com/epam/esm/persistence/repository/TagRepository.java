package com.epam.esm.persistence.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag> {

    /**
     * Finds all gift certificates associated with tag
     *
     * @param tagId ID of tag
     * @return List of associated gift certificates
     */
    List<GiftCertificate> findAssociatedGiftCertificates(Long tagId);

    /**
     * Finds tag by name
     *
     * @param name Name of tag to search
     * @return Found tag or {@code null}
     */
    Tag findByName(String name);

    List<Tag> findMostPopularTag();
}
