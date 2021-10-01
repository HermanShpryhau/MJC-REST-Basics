package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;

public interface TagService {
    Tag addTag(Tag tag);

    Tag fetchAllTags();

    Tag fetchTagById(Long id);

    Tag fetchTagByName(String name);

    GiftCertificateDto fetchAssociatedCertificates(Tag tag);

    void deleteTag(Long id);
}
