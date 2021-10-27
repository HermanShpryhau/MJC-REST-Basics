package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.RepositoryErrorCode;
import com.epam.esm.persistence.repository.RepositoryException;
import com.epam.esm.persistence.repository.filter.GiftCertificatesFilterConfig;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String SELECT_ALL_QUERY = "SELECT giftCertificate from GiftCertificate giftCertificate";
    private static final String SELECT_ASSOCIATED_ORDERS =
            "SELECT order FROM Order order WHERE order.giftCertificate=:giftCertificate";
    private static final String COUNT_ALL_QUERY = "SELECT COUNT(giftCertificate) FROM GiftCertificate giftCertificate";
    private static final String GIFT_CERTIFICATE_ENTITY_NAME = "gift certificate";
    private static final String ORDER_ENTITY_NAME = "order";

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
        if (hasAssociatedOrders(certificate)) {
            throw new RepositoryException(RepositoryErrorCode.DELETION_FORBIDDEN, GIFT_CERTIFICATE_ENTITY_NAME, id,
                    ORDER_ENTITY_NAME);
        }
        entityManager.remove(certificate);
        return true;
    }

    /**
     * Checks if gift certificate has orders associated with it.
     *
     * @param certificate Gift certificate entity
     * @return {@code true} if gift certificate has orders associated with it
     */
    private boolean hasAssociatedOrders(GiftCertificate certificate) {
        TypedQuery<Order> query = entityManager.createQuery(SELECT_ASSOCIATED_ORDERS, Order.class);
        query.setParameter("giftCertificate", certificate);
        List<Order> resultList = query.getResultList();
        return !resultList.isEmpty();
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

    @Override
    public int countAll() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT_ALL_QUERY, Long.class);
        return query.getSingleResult().intValue();
    }
}
