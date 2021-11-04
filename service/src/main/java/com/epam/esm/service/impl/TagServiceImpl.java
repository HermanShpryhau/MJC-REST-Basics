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
import com.epam.esm.service.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        Tag savedTag = tagRepository.save(tagEntity);
        return tagDtoSerializer.dtoFromEntity(savedTag);
    }

    @Override
    public List<TagDto> fetchAllTags(int page, int size) {
        page = PaginationUtil.correctPageIndex(page, size, tagRepository::countAll);
        return tagRepository.findAll(page, size).stream()
                .map(tagDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto fetchTagById(Long id) {
        Tag tag = Optional.ofNullable(tagRepository.findById(id))
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.TAG_NOT_FOUND, id));
        return tagDtoSerializer.dtoFromEntity(tag);
    }

    @Override
    public List<TagDto> fetchMostPopularTag() {
        return tagRepository.findMostPopularTag().stream()
                .map(tagDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> fetchAssociatedCertificates(Long id, int page, int size) {
        Tag tag = Optional.ofNullable(tagRepository.findById(id))
                .orElseThrow(() -> new ServiceException(ServiceErrorCode.TAG_NOT_FOUND, id));
        List<GiftCertificate> associatedGiftCertificates = tagRepository.findAssociatedGiftCertificates(tag.getId());
        page = PaginationUtil.correctPageIndex(page, size, associatedGiftCertificates::size);
        return associatedGiftCertificates.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .map(certificateDtoSerializer::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTag(Long id) {
        boolean isDeleted = tagRepository.delete(id);
        if (!isDeleted) {
            throw new ServiceException(ServiceErrorCode.TAG_NOT_FOUND, id);
        }
    }
}
