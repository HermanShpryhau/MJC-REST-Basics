package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.dto.serialization.DtoSerializer;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import com.epam.esm.persistence.repository.filter.SortAttribute;
import com.epam.esm.persistence.repository.filter.SortDirection;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final DtoSerializer<GiftCertificateDto, GiftCertificate> dtoSerializer;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository,
                                      TagRepository tagRepository,
                                      @Qualifier("giftCertificateDtoSerializer") DtoSerializer<GiftCertificateDto,
                                              GiftCertificate> dtoSerializer) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.dtoSerializer = dtoSerializer;
    }

    @Override
    @Transactional
    public GiftCertificateDto addCertificate(GiftCertificateDto dto) {
        dto.setId(null);
        GiftCertificate certificateEntity = dtoSerializer.dtoToEntity(dto);
        certificateEntity.setAssociatedTags(fetchRelatedTagEntities(certificateEntity.getAssociatedTags()));
        GiftCertificate savedCertificate = certificateRepository.save(certificateEntity);
        return dtoSerializer.dtoFromEntity(savedCertificate);
    }

    private List<Tag> fetchRelatedTagEntities(List<Tag> dtoTags) {
        return dtoTags.stream()
                .map(tag -> Optional.ofNullable(tagRepository.findByName(tag.getName()))
                                .orElseGet(() -> tagRepository.save(tag)))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> fetchCertificatesWithFilters(Optional<List<String>> tagNames,
                                                                 Optional<List<String>> sortTypes,
                                                                 Optional<String> searchPattern,
                                                                 int page, int size) {
        GiftCertificatesFilterConfig.Builder filterConfigBuilder = GiftCertificatesFilterConfig.builder();
        tagNames.ifPresent(filterConfigBuilder::withTags);
        addSortsToConfig(sortTypes, filterConfigBuilder);
        searchPattern.ifPresent(filterConfigBuilder::withSearchPattern);
        GiftCertificatesFilterConfig config = filterConfigBuilder.build();
        return certificateRepository.findWithFilters(config, page, size).stream()
                .map(dtoSerializer::dtoFromEntity).collect(Collectors.toList());
    }

    /**
     * Parses sort parameters strings and adds that sorts to filter configuration
     *
     * @param sortTypes           List of sort parameters strings
     * @param filterConfigBuilder {@link GiftCertificatesFilterConfig} builder
     */
    private void addSortsToConfig(Optional<List<String>> sortTypes, GiftCertificatesFilterConfig.Builder filterConfigBuilder) {
        if (sortTypes.isPresent()) {
            List<String> sorts = sortTypes.get();
            sorts.forEach(s -> {
                String[] elements = s.split("-");
                SortAttribute column = SortAttribute.inferAttribute(elements[0]);
                SortDirection direction = SortDirection.valueOf(elements[1].toUpperCase());
                filterConfigBuilder.withSort(column, direction);
            });
        }
    }

    @Override
    public GiftCertificateDto fetchCertificateById(Long id) {
        Optional<GiftCertificate> certificateOptional = Optional.ofNullable(certificateRepository.findById(id));
        return certificateOptional.map(dtoSerializer::dtoFromEntity)
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
        GiftCertificate updatedCertificate = certificateRepository.update(certificateToUpdate);
        return dtoSerializer.dtoFromEntity(updatedCertificate);
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
    }

    @Override
    public void deleteCertificate(long id) {
        boolean isDeleted = certificateRepository.delete(id);
        if (!isDeleted) {
            throw new ServiceException(ErrorCode.CERTIFICATE_NOT_FOUND, id);
        }
    }
}
