package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String SELECT_ALL_QUERY = "SELECT giftCertificate from GiftCertificate giftCertificate";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificate save(GiftCertificate entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        return entityManager
                .createQuery(SELECT_ALL_QUERY, GiftCertificate.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public GiftCertificate findById(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        GiftCertificate certificate = entityManager.find(GiftCertificate.class, id);
        if (certificate == null) {
            return false;
        }
        entityManager.remove(certificate);
        return true;
    }

    @Override
    public List<GiftCertificate> findWithFilters(GiftCertificatesFilterConfig config, int page, int size) {
        CriteriaQuery<GiftCertificate> criteriaQuery = config.asCriteriaQuery(entityManager.getCriteriaBuilder());
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }

    @Override
    public List<Tag> findAssociatedTags(Long certificateId) {
        return new ArrayList<>(entityManager.find(GiftCertificate.class, certificateId).getAssociatedTags());
    }
}
