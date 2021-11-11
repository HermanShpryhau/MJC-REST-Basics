package com.epam.esm.web.controller;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.validation.PatchDto;
import com.epam.esm.model.validation.SaveDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.web.hateoas.assembler.GiftCertificateModelAssembler;
import com.epam.esm.web.hateoas.assembler.TagModelAssembler;
import com.epam.esm.web.hateoas.model.GiftCertificateModel;
import com.epam.esm.web.hateoas.model.TagModel;
import com.epam.esm.web.hateoas.processor.GiftCertificateModelProcessor;
import com.epam.esm.web.hateoas.processor.TagModelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller of gift certificates resource.
 */
@RestController
@RequestMapping("/certificates")
public class CertificatesController {
    private final GiftCertificateService certificateService;
    private final GiftCertificateModelAssembler certificateModelAssembler;
    private final GiftCertificateModelProcessor certificateModelProcessor;
    private final TagModelAssembler tagModelAssembler;
    private final TagModelProcessor tagModelProcessor;

    @Autowired
    public CertificatesController(GiftCertificateService certificateService,
                                  GiftCertificateModelAssembler certificateModelAssembler,
                                  GiftCertificateModelProcessor certificateModelProcessor,
                                  TagModelAssembler tagModelAssembler,
                                  TagModelProcessor tagModelProcessor) {
        this.certificateService = certificateService;
        this.certificateModelAssembler = certificateModelAssembler;
        this.certificateModelProcessor = certificateModelProcessor;
        this.tagModelAssembler = tagModelAssembler;
        this.tagModelProcessor = tagModelProcessor;
    }

    /**
     * Gets gift certificate DTOs matching supplied filtering parameters.
     *
     * @param tagNames      List of tag names to search certificates by
     * @param sortTypes     Names of parameters ond sort directions to sort by. String must follow the pattern {@code
     *                      [parameter name]-[asc|desc]}
     * @param searchPattern String to search for in name or description of the certificate
     * @param page          Index of page
     * @param size          Size of page
     * @return List of matching gift certificate DTOs
     */
    @GetMapping
    public CollectionModel<GiftCertificateModel> getAllCertificatesWithFilters(
            @RequestParam("tag") Optional<List<String>> tagNames,
            @RequestParam("sort") Optional<List<String>> sortTypes,
            @RequestParam("search") Optional<String> searchPattern,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        Page<GiftCertificateDto> giftCertificatesPage = certificateService.fetchCertificatesWithFilters(tagNames, sortTypes,
                searchPattern, page, size);
        CollectionModel<GiftCertificateModel> collectionModel =
                certificateModelAssembler.toCollectionModel(giftCertificatesPage.getContent());
        return certificateModelProcessor.process(tagNames, sortTypes, searchPattern, size, giftCertificatesPage, collectionModel);
    }

    /**
     * Gets gift certificate DTO by ID.
     *
     * @param id ID of gift certificate to find
     * @return Gift certificate DTO derived from gift certificate entity with corresponding ID
     */
    @GetMapping("/{id}")
    public GiftCertificateModel getById(@PathVariable long id) {
        GiftCertificateDto certificateDto = certificateService.fetchCertificateById(id);
        return certificateModelAssembler.toModel(certificateDto);
    }

    /**
     * Gets tags associated with gift certificate.
     *
     * @param id   ID of gift certificate to find associated tags for
     * @param page Index of page
     * @param size Size of page
     * @return List of associated tags
     */
    @GetMapping("/{id}/tags")
    public CollectionModel<TagModel> getAssociatedTags(@PathVariable long id,
                                                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagDtoPage = certificateService.fetchAssociatedTags(id, page, size);
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagDtoPage.getContent());
        return tagModelProcessor.process(id, tagDtoPage, size, collectionModel);
    }

    /**
     * Saves gift certificate to data source.
     *
     * @param certificate Gift certificate DTO
     * @return DTO derived from saved entity
     */
    @PostMapping
    public GiftCertificateModel addCertificate(@Validated(SaveDto.class) @RequestBody GiftCertificateDto certificate) {
        GiftCertificateDto certificateDto = certificateService.addCertificate(certificate);
        return certificateModelAssembler.toModel(certificateDto);
    }

    /**
     * Updates gift certificate in data source with data supplied DTO.
     *
     * @param certificate DTO containing updated data. Only the supplied data will be updated. The rest will remain
     *                    unchanged.
     * @return Updated gift certificate DTO
     */
    @PatchMapping
    public GiftCertificateModel updateCertificate(@Validated(PatchDto.class) @RequestBody GiftCertificateDto certificate) {
        GiftCertificateDto certificateDto = certificateService.updateCertificate(certificate);
        return certificateModelAssembler.toModel(certificateDto);
    }

    /**
     * Deletes gift certificate from data source.
     *
     * @param id ID of gift certificate to delete
     */
    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable long id) {
        certificateService.deleteCertificate(id);
    }
}
