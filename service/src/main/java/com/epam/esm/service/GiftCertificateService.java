package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.pagination.Page;

import java.util.List;
import java.util.Optional;

/**
 * {@code GiftCertificateService} is an interface that contains all operations available for gift certificates
 * resource of the API.
 */
public interface GiftCertificateService {

    /**
     * Saves gift certificate entity derived from provided DTO to data source.
     *
     * @param dto Gift certificate DTO
     * @return DTO derived from saved entity
     */
    GiftCertificateDto addCertificate(GiftCertificateDto dto);

    /**
     * Fetches list of gift certificate DTOs filtered by supplied parameters.
     *
     * @param tagNames      List of tag names to search gift certificates by
     * @param sortTypes     Names of parameters ond sort directions to sort by. String must follow the pattern {@code
     *                      [parameter name]-[asc|desc]}
     * @param searchPattern String to search for in name or description of the certificate
     * @param page          Index of page
     * @param size          Size of page
     * @return List of certificate that confirm to supplied filtering parameters
     */
    Page<GiftCertificateDto> fetchCertificatesWithFilters(Optional<List<String>> tagNames,
                                                          Optional<List<String>> sortTypes,
                                                          Optional<String> searchPattern,
                                                          int page, int size);

    /**
     * Fetches gift certificate DTO by ID.
     *
     * @param id ID of gift certificate to find
     * @return Gift certificate DTO derived from gift certificate entity with corresponding ID
     */
    GiftCertificateDto fetchCertificateById(Long id);

    /**
     * Fetches all tags associated with gift certificate.
     *
     * @param certificateId ID of gift certificate to find associated tags for
     * @return List of associated tags DTOS
     */
    Page<TagDto> fetchAssociatedTags(long certificateId, int page, int size);

    /**
     * Updates gift certificate entity in data source with data supplied DTO.
     *
     * @param dto DTO containing updated data.
     * @return Gift certificate DTO derived from updated gift certificate entity
     */
    GiftCertificateDto updateCertificate(GiftCertificateDto dto);

    /**
     * Deletes gift certificate from data source.
     *
     * @param id ID of gift certificate to delete
     */
    void deleteCertificate(long id);
}
