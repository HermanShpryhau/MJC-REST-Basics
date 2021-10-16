package com.epam.esm.web.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Controller of tags resource.
 */

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Saves tag to data source.
     * @param tag Tag to save
     * @return Saved tag
     */
    @PostMapping
    public Tag addTag(@Validated @RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    /**
     * Gets all tags in data source.
     * @return List of found tags
     */
    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.fetchAllTags();
    }

    /**
     * Gets tag by ID.
     * @param id ID of tag to fetch
     * @return Found tag entity
     */
    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) {
        return tagService.fetchTagById(id);
    }

    /**
     * Gets certificates associated with tag.
     * @param id ID of gift certificate to find tags for
     * @return List of associated tags
     */
    @GetMapping("/{id}/certificates")
    public List<GiftCertificateDto> getAssociatedCertificates(@PathVariable Long id) {
        return tagService.fetchAssociatedCertificates(id);
    }

    /**
     * Deletes tag from data source
     * @param id ID of tag entity to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
