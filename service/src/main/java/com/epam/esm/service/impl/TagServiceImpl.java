package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.GiftCertificateDtoTranslator;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final GiftCertificateDtoTranslator dtoTranslator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, GiftCertificateDtoTranslator dtoTranslator) {
        this.tagRepository = tagRepository;
        this.dtoTranslator = dtoTranslator;
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> fetchAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag fetchTagById(Long id) {
        return Optional.ofNullable(tagRepository.findById(id))
                .orElseThrow(() -> new ServiceException(ErrorCode.TAG_NOT_FOUND, id));
    }

    @Override
    public Tag fetchTagByName(String name) {
        return Optional.ofNullable(tagRepository.findByName(name))
                .orElseThrow(() -> new ServiceException(ErrorCode.TAG_NOT_FOUND));
    }

    @Override
    public List<GiftCertificateDto> fetchAssociatedCertificates(Long id) {
        Tag tag = Optional.ofNullable(tagRepository.findById(id))
                .orElseThrow(() -> new ServiceException(ErrorCode.TAG_NOT_FOUND, id));
        return tagRepository.findAssociatedGiftCertificates(tag.getId()).stream()
                .map(dtoTranslator::giftCertificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTag(Long id) {
        boolean isDeleted = tagRepository.delete(id);
        if (!isDeleted) {
            throw new ServiceException(ErrorCode.TAG_NOT_FOUND, id);
        }
    }
}
