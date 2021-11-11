package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import org.springframework.data.domain.Page;

/**
 * {@code TagService} is an interface that contains all operations available for tags resource of the API.
 */
public interface TagService {

    /**
     * Saves tag to data source.
     *
     * @param tag Tag entity to save
     * @return Saved tag entity
     */
    TagDto addTag(TagDto tag);

    /**
     * Fetches all tags in data source.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of found tags
     */
    Page<TagDto> fetchAllTags(int page, int size);

    /**
     * Fetches tag by ID.
     *
     * @param id ID of tag to fetch
     * @return Found tag entity
     */
    TagDto fetchTagById(Long id);

    /**
     * Fetches most widely used tag(s) of a user with the highest cost of all orders.
     *
     * @return List of tags matching criteria.
     */
    Page<TagDto> fetchMostPopularTag();

    /**
     * Fetches certificates associated with tag.
     *
     * @param id ID of gift certificate to find tags for
     * @return List of associated tags
     */
    Page<GiftCertificateDto> fetchAssociatedCertificates(Long id, int page, int size);

    /**
     * Deletes tag from data source
     *
     * @param id ID of tag entity to delete
     */
    void deleteTag(Long id);
}
