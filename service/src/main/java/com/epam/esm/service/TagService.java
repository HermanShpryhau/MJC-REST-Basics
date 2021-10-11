package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

import java.util.List;

public interface TagService {

    /**
     * Saves tag to data source.
     * @param tag Tag entity to save
     * @return Saved tag entity
     */
    Tag addTag(Tag tag);

    /**
     * Fetches all tags in data source.
     * @return List of found tags
     */
    List<Tag> fetchAllTags();

    /**
     * Fetches tag by ID.
     * @param id ID of tag to fetch
     * @return Found tag entity
     */
    Tag fetchTagById(Long id);

    /**
     * Fetches tag by name.
     * @param name Name of tag to fetch
     * @return Found tag entity
     */
    Tag fetchTagByName(String name);

    /**
     * Fetches certificates associated with tag.
     * @param id ID of gift certificate to find tags for
     * @return List of associated tags
     */
    List<GiftCertificateDto> fetchAssociatedCertificates(Long id);

    /**
     * Deletes tag from data source
     * @param id ID of tag entity to delete
     */
    void deleteTag(Long id);
}
