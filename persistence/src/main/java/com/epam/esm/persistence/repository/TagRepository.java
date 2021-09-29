package com.epam.esm.persistence.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag> {

    List<GiftCertificate> findAssociatedGiftCertificates(Long tagId);

}
