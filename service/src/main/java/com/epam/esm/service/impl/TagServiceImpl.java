package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.serialization.DtoSerializer;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer;
    private final DtoSerializer<GiftCertificateDto, GiftCertificate> certificateDtoSerializer;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          @Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer,
                          @Qualifier("giftCertificateDtoSerializer") DtoSerializer<GiftCertificateDto,
                                  GiftCertificate> certificateDtoSerializer) {
        this.tagRepository = tagRepository;
        this.tagDtoSerializer = tagDtoSerializer;
        this.certificateDtoSerializer = certificateDtoSerializer;
    }

    @Override
    public TagDto addTag(TagDto tag) {
        Tag tagEntity = tagDtoSerializer.dtoToEntity(tag);
        tagEntity.setId(null);
        Tag savedTag = tagRepository.save(tagEntity);
        return tagDtoSerializer.dtoFromEntity(savedTag);
    }

    @Override
    public Page<TagDto> fetchAllTags(int page, int size) {
        return tagRepository.findAll(PageRequest.of(page, size, Sort.by("id")))
                .map(tagDtoSerializer::dtoFromEntity);
    }

    @Override
    public TagDto fetchTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.TAG_NOT_FOUND, id));
        return tagDtoSerializer.dtoFromEntity(tag);
    }

    @Override
    public Page<TagDto> fetchMostPopularTag() {
        List<TagDto> tagDtos = tagRepository.findMostPopularTag().stream()
                .map(tagDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(tagDtos, PageRequest.of(0, tagDtos.size()), tagDtos.size());
    }

    @Override
    @Transactional
    public Page<GiftCertificateDto> fetchAssociatedCertificates(Long id, int page, int size) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.TAG_NOT_FOUND, id));
        Set<GiftCertificate> associatedGiftCertificates = tag.getAssociatedCertificates();
        List<GiftCertificateDto> associatedCertificatesDtos = associatedGiftCertificates.stream()
                .skip((long) (page) * size)
                .limit(size)
                .map(certificateDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(associatedCertificatesDtos, PageRequest.of(page, size), associatedGiftCertificates.size());
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.TAG_NOT_FOUND, id));
        tagRepository.delete(tag);
    }
}
