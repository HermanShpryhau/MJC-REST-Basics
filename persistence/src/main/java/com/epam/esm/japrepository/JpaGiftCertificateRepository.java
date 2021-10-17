package com.epam.esm.japrepository;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, PagingAndSortingRepository<GiftCertificate, Long> {
}