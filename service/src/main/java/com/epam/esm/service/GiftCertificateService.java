package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

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
     * @param tagName       Name of tag to search for in gift certificates
     * @param sortTypes     Names of parameters ond sort directions to sort by. String must follow the pattern {@code
     * [parameter name]-[asc|desc]}
     * @param searchPattern String to search for in name or description of the certificate
     * @return List of certificate that confirm to supplied filtering parameters
     */
    List<GiftCertificateDto> fetchCertificatesWithFilters(Optional<String> tagName,
                                                          Optional<List<String>> sortTypes,
                                                          Optional<String> searchPattern);

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
     * @return List of associated tags
     */
    List<Tag> fetchAssociatedTags(long certificateId);

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
