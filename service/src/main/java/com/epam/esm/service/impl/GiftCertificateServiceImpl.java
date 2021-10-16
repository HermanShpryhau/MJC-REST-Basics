package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;
import com.epam.esm.persistence.repository.filter.SortColumn;
import com.epam.esm.persistence.repository.filter.SortDirection;
import com.epam.esm.service.GiftCertificateDtoTranslator;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link GiftCertificateService}.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateDtoTranslator dtoTranslator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository,
                                      TagRepository tagRepository,
                                      GiftCertificateDtoTranslator dtoTranslator) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.dtoTranslator = dtoTranslator;
    }

    @Override
    @Transactional
    public GiftCertificateDto addCertificate(GiftCertificateDto dto) {
        GiftCertificate savedCertificate = certificateRepository.save(dtoTranslator.dtoToGiftCertificate(dto));
        List<Tag> savedTags = updateTags(savedCertificate.getId(), dto.getTags());
        return dtoTranslator.giftCertificateToDto(savedCertificate, savedTags);
    }

    /**
     * Updates list of tags associated with gift certificate.
     *
     * @param certificateId ID of gift certificate
     * @param newTags       List of tags that must be associated with certificate after update
     * @return List of tags that were associated with certificate after update.
     */
    private List<Tag> updateTags(Long certificateId, List<Tag> newTags) {
        unlinkTags(certificateId);
        return linkTags(certificateId, newTags);
    }

    /**
     * Removes all associations between gift certificate and it's tags.
     *
     * @param certificateId ID of gift certificate
     */
    private void unlinkTags(Long certificateId) {
        Set<Tag> oldTagsSet = new HashSet<>(certificateRepository.findAssociatedTags(certificateId));
        oldTagsSet.forEach((tag) -> certificateRepository.removeTagAssociation(certificateId, tag.getId()));
    }

    /**
     * Associates tags with gift certificate.
     *
     * @param certificateId ID of gift certificate
     * @param newTags       List of tags that must be associated with certificate after update
     * @return List of tags associated with certificate.
     */
    private List<Tag> linkTags(Long certificateId, List<Tag> newTags) {
        Set<Tag> newTagsSet = new HashSet<>(newTags);
        List<Tag> associatedTags = new ArrayList<>();
        newTagsSet.forEach((tag) -> associatedTags.add(addTagToCertificate(certificateId, tag.getName())));
        return associatedTags;
    }

    /**
     * Adds association between tag and gift certificate.
     *
     * @param certificateId ID of gift certificate
     * @param tagName       Name of tag to make association with
     * @return Tag entity that was associated with gift certificate.
     */
    private Tag addTagToCertificate(Long certificateId, String tagName) {
        Optional<Tag> tagToAdd = Optional.ofNullable(tagRepository.findByName(tagName));
        Tag tag = tagToAdd.orElse(tagRepository.save(new Tag(tagName)));
        certificateRepository.addTagAssociation(certificateId, tag.getId());
        return tag;
    }

    @Override
    public List<GiftCertificateDto> fetchCertificatesWithFilters(Optional<String> tagName,
                                                                 Optional<List<String>> sortTypes,
                                                                 Optional<String> searchPattern) {
        QueryFiltersConfig.Builder filterConfigBuilder = QueryFiltersConfig.builder();
        tagName.ifPresent(filterConfigBuilder::withTag);
        addSortsToConfig(sortTypes, filterConfigBuilder);
        searchPattern.ifPresent(filterConfigBuilder::withSearchPattern);
        QueryFiltersConfig config = filterConfigBuilder.build();
        return certificateRepository.findWithFilters(config).stream()
                .map(dtoTranslator::giftCertificateToDto).collect(Collectors.toList());
    }

    /**
     * Parses sort parameters strings and adds that sorts to filter configuration
     *
     * @param sortTypes           List of sort parameters strings
     * @param filterConfigBuilder {@link QueryFiltersConfig} builder
     */
    private void addSortsToConfig(Optional<List<String>> sortTypes, QueryFiltersConfig.Builder filterConfigBuilder) {
        if (sortTypes.isPresent()) {
            List<String> sorts = sortTypes.get();
            sorts.forEach((s) -> {
                String[] elements = s.split("-");
                SortColumn column = SortColumn.tryInferColumn(elements[0]);
                SortDirection direction = SortDirection.valueOf(elements[1].toUpperCase());
                filterConfigBuilder.withSort(column, direction);
            });
        }
    }

    @Override
    public GiftCertificateDto fetchCertificateById(Long id) {
        Optional<GiftCertificate> certificateOptional = Optional.ofNullable(certificateRepository.findById(id));
        return certificateOptional.map(dtoTranslator::giftCertificateToDto)
                .orElseThrow(() -> new ServiceException(ErrorCode.CERTIFICATE_NOT_FOUND, id));
    }

    @Override
    public List<Tag> fetchAssociatedTags(long certificateId) {
        if (certificateRepository.findById(certificateId) == null) {
            throw new ServiceException(ErrorCode.CERTIFICATE_NOT_FOUND, certificateId);
        }
        return certificateRepository.findAssociatedTags(certificateId);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto dto) {
        GiftCertificate certificateToUpdate = Optional.ofNullable(certificateRepository.findById(dto.getId()))
                .orElseThrow(() -> new ServiceException(ErrorCode.CERTIFICATE_NOT_FOUND, dto.getId()));
        updateSpecifiedParameters(dto, certificateToUpdate);
        GiftCertificate updatedCertificate = certificateRepository.update(certificateToUpdate.getId(),
                certificateToUpdate);
        return dtoTranslator.giftCertificateToDto(updatedCertificate);
    }

    /**
     * Updates state of gift certificate entity with state of supplied DTO.
     *
     * @param dto                 Gift certificate DTO
     * @param certificateToUpdate Updated entity
     */
    private void updateSpecifiedParameters(GiftCertificateDto dto, GiftCertificate certificateToUpdate) {
        Optional.ofNullable(dto.getName()).ifPresent(certificateToUpdate::setName);
        Optional.ofNullable(dto.getDescription()).ifPresent(certificateToUpdate::setDescription);
        Optional.ofNullable(dto.getPrice()).ifPresent(certificateToUpdate::setPrice);
        Optional.ofNullable(dto.getDuration()).ifPresent(certificateToUpdate::setDuration);
        updateTags(certificateToUpdate.getId(), dto.getTags());
    }

    @Override
    public void deleteCertificate(long id) {
        boolean isDeleted = certificateRepository.delete(id);
        if (!isDeleted) {
            throw new ServiceException(ErrorCode.CERTIFICATE_NOT_FOUND, id);
        }
    }
}
