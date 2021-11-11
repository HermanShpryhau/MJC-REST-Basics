package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.serialization.DtoSerializer;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import com.epam.esm.persistence.repository.filter.SortAttribute;
import com.epam.esm.persistence.repository.filter.SortDirection;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final DtoSerializer<GiftCertificateDto, GiftCertificate> dtoSerializer;
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository,
                                      TagRepository tagRepository,
                                      @Qualifier("giftCertificateDtoSerializer") DtoSerializer<GiftCertificateDto,
                                              GiftCertificate> dtoSerializer,
                                      @Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.dtoSerializer = dtoSerializer;
        this.tagDtoSerializer = tagDtoSerializer;
    }

    @Override
    @Transactional
    public GiftCertificateDto addCertificate(GiftCertificateDto dto) {
        GiftCertificate certificateEntity = dtoSerializer.dtoToEntity(dto);
        certificateEntity.setId(null);
        certificateEntity.setAssociatedTags(fetchRelatedTagEntities(certificateEntity.getAssociatedTags()));
        GiftCertificate savedCertificate = certificateRepository.save(certificateEntity);
        return dtoSerializer.dtoFromEntity(savedCertificate);
    }

    private Set<Tag> fetchRelatedTagEntities(Set<Tag> dtoTags) {
        return dtoTags.stream()
                .map(tag -> tagRepository.findByName(tag.getName())
                        .orElseGet(() -> tagRepository.save(tag)))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Page<GiftCertificateDto> fetchCertificatesWithFilters(Optional<List<String>> tagNames,
                                                                 Optional<List<String>> sortTypes,
                                                                 Optional<String> searchPattern,
                                                                 int page, int size) {
        GiftCertificatesFilterConfig.Builder filterConfigBuilder = GiftCertificatesFilterConfig.builder();
        tagNames.ifPresent(filterConfigBuilder::withTags);
        addSortsToConfig(sortTypes, filterConfigBuilder);
        searchPattern.ifPresent(filterConfigBuilder::withSearchPattern);
        GiftCertificatesFilterConfig config = filterConfigBuilder.build();
        return certificateRepository.findWithFilters(config, page, size)
                .map(dtoSerializer::dtoFromEntity);
    }

    /**
     * Parses sort parameters strings and adds that sorts to filter configuration
     *
     * @param sortTypes           List of sort parameters strings
     * @param filterConfigBuilder {@link GiftCertificatesFilterConfig} builder
     */
    private void addSortsToConfig(Optional<List<String>> sortTypes,
                                  GiftCertificatesFilterConfig.Builder filterConfigBuilder) {
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
        Optional<GiftCertificate> certificateOptional = certificateRepository.findById(id);
        return certificateOptional.map(dtoSerializer::dtoFromEntity)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.CERTIFICATE_NOT_FOUND, id));
    }

    @Override
    public Page<TagDto> fetchAssociatedTags(long certificateId, int page, int size) {
        GiftCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() ->  new ServiceException(ServiceErrorCode.CERTIFICATE_NOT_FOUND, certificateId));
        Set<Tag> associatedTags = certificate.getAssociatedTags();
        int entitiesCount = associatedTags.size();
        List<TagDto> tagDtos = associatedTags.stream()
                .skip((long) (page) * size)
                .limit(size)
                .map(tagDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(tagDtos, PageRequest.of(page, size), entitiesCount);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto dto) {
        GiftCertificate certificateToUpdate = certificateRepository.findById(dto.getId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.CERTIFICATE_NOT_FOUND, dto.getId()));
        updateSpecifiedParameters(dto, certificateToUpdate);
        GiftCertificate updatedCertificate = certificateRepository.save(certificateToUpdate);
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
        Optional.ofNullable(dto.getTags())
                .ifPresent((tagDtos -> certificateToUpdate.setAssociatedTags(buildTagListFromDtos(tagDtos))));
    }

    /**
     * Builds a list of tag entites from supplied DTOs list. Creates new tag entity if it is not present.
     *
     * @param dtoTags List of tag DTOs.
     * @return List of tag entities.
     */
    private Set<Tag> buildTagListFromDtos(List<TagDto> dtoTags) {
        return dtoTags.stream()
                .map(tagDto -> tagRepository.findByName(tagDto.getName())
                        .orElseGet(() -> tagRepository.save(tagDtoSerializer.dtoToEntity(tagDto))))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteCertificate(long id) {
        GiftCertificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.CERTIFICATE_NOT_FOUND, id));
        certificateRepository.delete(certificate);
    }
}
