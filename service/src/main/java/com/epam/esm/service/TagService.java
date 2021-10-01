package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

import java.util.List;

public interface TagService {
    Tag addTag(Tag tag);

    List<Tag> fetchAllTags();

    Tag fetchTagById(Long id);

    Tag fetchTagByName(String name);

    List<GiftCertificateDto> fetchAssociatedCertificates(Long id);

    void deleteTag(Long id);
}
