package com.epam.esm.persistence.repository;

import com.epam.esm.model.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        CustomGiftCertificateRepository {
}
