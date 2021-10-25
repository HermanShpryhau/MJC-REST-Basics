package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param tag Tag to save
     * @return Saved tag
     */
    @PostMapping
    public TagDto addTag(@Validated @RequestBody TagDto tag) {
        return tagService.addTag(tag);
    }

    /**
     * Gets all tags in data source.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of found tags
     */
    @GetMapping
    public List<TagDto> getAllTags(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return tagService.fetchAllTags(page, size);
    }

    /**
     * Gets tag by ID.
     *
     * @param id ID of tag to fetch
     * @return Found tag entity
     */
    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Long id) {
        return tagService.fetchTagById(id);
    }

    /**
     * Gets certificates associated with tag.
     *
     * @param id ID of gift certificate to find tags for
     * @param page Index of page
     * @param size Size of page
     * @return List of associated tags
     */
    @GetMapping("/{id}/certificates")
    public List<GiftCertificateDto> getAssociatedCertificates(@PathVariable Long id,
                                                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return tagService.fetchAssociatedCertificates(id, page, size);
    }

    @GetMapping("/most-popular")
    public List<TagDto> getMostPopularTag() {
        return tagService.fetchMostPopularTag();
    }

    /**
     * Deletes tag from data source
     *
     * @param id ID of tag entity to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
