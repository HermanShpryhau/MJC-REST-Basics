package com.epam.esm.web.controller;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.web.hateoas.assembler.GiftCertificateModelAssembler;
import com.epam.esm.web.hateoas.assembler.TagModelAssembler;
import com.epam.esm.web.hateoas.model.GiftCertificateModel;
import com.epam.esm.web.hateoas.model.TagModel;
import com.epam.esm.web.hateoas.processor.GiftCertificateModelProcessor;
import com.epam.esm.web.hateoas.processor.TagModelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller of tags resource.
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;
    private final TagModelProcessor tagModelProcessor;
    private final GiftCertificateModelAssembler certificateModelAssembler;
    private final GiftCertificateModelProcessor certificateModelProcessor;

    @Autowired
    public TagController(TagService tagService,
                         TagModelAssembler tagModelAssembler,
                         TagModelProcessor tagModelProcessor,
                         GiftCertificateModelAssembler certificateModelAssembler,
                         GiftCertificateModelProcessor certificateModelProcessor) {
        this.tagService = tagService;
        this.tagModelAssembler = tagModelAssembler;
        this.tagModelProcessor = tagModelProcessor;
        this.certificateModelAssembler = certificateModelAssembler;
        this.certificateModelProcessor = certificateModelProcessor;
    }

    /**
     * Saves tag to data source.
     *
     * @param tag Tag to save
     * @return Saved tag
     */
    @PostMapping
    public TagModel addTag(@Validated @RequestBody TagDto tag) {
        TagDto tagDto = tagService.addTag(tag);
        return tagModelAssembler.toModel(tagDto);
    }

    /**
     * Gets all tags in data source.
     *
     * @param page Index of page
     * @param size Size of page
     * @return List of found tags
     */
    @GetMapping
    public CollectionModel<TagModel> getAllTags(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagDtos = tagService.fetchAllTags(page, size);
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagDtos.getContent());
        return tagModelProcessor.process(tagDtos, size, collectionModel);
    }

    /**
     * Gets tag by ID.
     *
     * @param id ID of tag to fetch
     * @return Found tag
     */
    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Long id) {
        return tagService.fetchTagById(id);
    }

    /**
     * Gets certificates associated with tag.
     *
     * @param id   ID of gift certificate to find tags for
     * @param page Index of page
     * @param size Size of page
     * @return List of associated certificates
     */
    @GetMapping("/{id}/certificates")
    public CollectionModel<GiftCertificateModel> getAssociatedCertificates(@PathVariable Long id,
                                                                           @RequestParam(name = "page", defaultValue
                                                                                   = "1") Integer page,
                                                                           @RequestParam(name = "size", defaultValue
                                                                                   = "10") Integer size) {
        Page<GiftCertificateDto> giftCertificateDtos = tagService.fetchAssociatedCertificates(id, page, size);
        CollectionModel<GiftCertificateModel> collectionModel =
                certificateModelAssembler.toCollectionModel(giftCertificateDtos.getContent());
        return certificateModelProcessor.process(id, size, giftCertificateDtos, collectionModel);
    }

    /**
     * Gets most widely used tag(s) of a user with the highest cost of all orders.
     *
     * @return List of tags matching criteria
     */
    @GetMapping("/most-popular")
    public CollectionModel<TagModel> getMostPopularTag() {
        Page<TagDto> tagDtos = tagService.fetchMostPopularTag();
        return tagModelAssembler.toCollectionModel(tagDtos.getContent());
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
