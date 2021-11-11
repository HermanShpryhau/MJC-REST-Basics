package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.persistence.repository.CustomGiftCertificateRepository;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

@Repository
public class CustomGiftCertificateRepositoryImpl implements CustomGiftCertificateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<GiftCertificate> findWithFilters(GiftCertificatesFilterConfig config, int page, int size) {
        CriteriaQuery<GiftCertificate> criteriaQuery = config.asCriteriaQuery(entityManager.getCriteriaBuilder());
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page) * size);
        typedQuery.setMaxResults(size);
        return new PageImpl<>(typedQuery.getResultList(), PageRequest.of(page, size), countEntitiesWithFilter(config));
    }

    public int countEntitiesWithFilter(GiftCertificatesFilterConfig config) {
        CriteriaQuery<GiftCertificate> criteriaQuery = config.asCriteriaQuery(entityManager.getCriteriaBuilder());
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList().size();
    }
}
