package com.epam.esm.web.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.validation.PatchDto;
import com.epam.esm.domain.validation.SaveDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller of gift certificates resource.
 */

@RestController
@RequestMapping("/certificates")
public class CertificatesController {
    private final GiftCertificateService certificateService;

    @Autowired
    public CertificatesController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Gets gift certificate DTOs matching supplied filtering parameters.
     *
     * @param tagNames      List of tag names to search certificates by
     * @param sortTypes     Names of parameters ond sort directions to sort by. String must follow the patter {@code [parameter name]-[asc|desc]}
     * @param searchPattern String to search for in name or description of the certificate
     * @param page          Index of page
     * @param size          Size of page
     * @return List of matching gift certificate DTOs
     */
    @GetMapping
    public List<GiftCertificateDto> getAllCertificatesWithFilters(
            @RequestParam("tag") Optional<List<String>> tagNames,
            @RequestParam("sort") Optional<List<String>> sortTypes,
            @RequestParam("search") Optional<String> searchPattern,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return certificateService.fetchCertificatesWithFilters(tagNames, sortTypes, searchPattern, page, size);
    }

    /**
     * Gets gift certificate DTO by ID.
     *
     * @param id ID of gift certificate to find
     * @return Gift certificate DTO derived from gift certificate entity with corresponding ID
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getById(@PathVariable long id) {
        return certificateService.fetchCertificateById(id);
    }

    /**
     * Gets tags associated with gift certificate.
     *
     * @param id ID of gift certificate to find associated tags for
     * @return List of associated tags
     */
    @GetMapping("/{id}/tags")
    public List<Tag> getAssociatedTags(@PathVariable long id) {
        return certificateService.fetchAssociatedTags(id);
    }

    /**
     * Saves gift certificate to data source.
     *
     * @param certificate Gift certificate DTO
     * @return DTO derived from saved entity
     */
    @PostMapping
    public GiftCertificateDto addCertificate(@Validated(SaveDto.class) @RequestBody GiftCertificateDto certificate) {
        return certificateService.addCertificate(certificate);
    }

    /**
     * Updates gift certificate in data source with data supplied DTO.
     *
     * @param certificate DTO containing updated data. Only the supplied data will be updated. The rest will remain unchanged.
     * @return Updated gift certificate DTO
     */
    @PatchMapping
    public GiftCertificateDto updateCertificate(@Validated(PatchDto.class) @RequestBody GiftCertificateDto certificate) {
        return certificateService.updateCertificate(certificate);
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
