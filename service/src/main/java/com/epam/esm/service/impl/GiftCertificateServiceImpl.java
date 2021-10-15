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

import java.util.*;
import java.util.stream.Collectors;

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

    private List<Tag> updateTags(Long certificateId, List<Tag> newTags) {
        unlinkTags(certificateId);
        return relinkTags(certificateId, newTags);
    }

    private void unlinkTags(Long certificateId) {
        Set<Tag> oldTagsSet = new HashSet<>(certificateRepository.findAssociatedTags(certificateId));
        oldTagsSet.forEach((tag) -> certificateRepository.removeTagAssociation(certificateId, tag.getId()));
    }

    private List<Tag> relinkTags(Long certificateId, List<Tag> newTags) {
        Set<Tag> newTagsSet = new HashSet<>(newTags);
        List<Tag> associatedTags = new ArrayList<>();
        newTagsSet.forEach((tag) -> associatedTags.add(addTagToCertificate(certificateId, tag.getName())));
        return associatedTags;
    }

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
        GiftCertificate updatedCertificate = certificateRepository.update(certificateToUpdate.getId(), certificateToUpdate);
        return dtoTranslator.giftCertificateToDto(updatedCertificate);
    }

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
