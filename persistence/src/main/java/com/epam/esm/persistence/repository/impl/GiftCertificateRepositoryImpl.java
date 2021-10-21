package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;
import com.epam.esm.persistence.repository.filter.SortAttribute;
import com.epam.esm.persistence.repository.filter.SortDirection;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public boolean delete(Long id) {
        GiftCertificate certificate = entityManager.find(GiftCertificate.class, id);
        if (certificate == null) {
            return false;
        }
        entityManager.remove(certificate);
        return true;
    }

    @Override
    public List<GiftCertificate> findWithFilters(QueryFiltersConfig config, int page, int size) {
        CriteriaQuery<GiftCertificate> criteriaQuery = buildCriteriaQuery(config);
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }

    private CriteriaQuery<GiftCertificate> buildCriteriaQuery(QueryFiltersConfig config) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        Predicate[] predicatesArray = buildPredicates(config, criteriaBuilder, root);

        List<Order> sortOrders = buildSortingCriteria(config, criteriaBuilder, root);

        criteriaQuery.orderBy(sortOrders).select(root).where(predicatesArray);
        return criteriaQuery;
    }

    private Predicate[] buildPredicates(QueryFiltersConfig config, CriteriaBuilder criteriaBuilder,
                                        Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (config.hasSearchPattern()) {
            predicates.add(buildSearchPatternPredicate(config.getSearchPattern(), criteriaBuilder, root));
        }
        if (config.hasTags()) {
            predicates.add(buildTagsPredicate(config.getTags(), criteriaBuilder, root));
        }
        return toPredicatesArray(predicates);
    }

    private Predicate buildSearchPatternPredicate(String searchPattern, CriteriaBuilder criteriaBuilder,
                                                  Root<GiftCertificate> root) {
        return criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), "%" + searchPattern + "%"),
                criteriaBuilder.like(root.get("description"), "%" + searchPattern + "%")
        );
    }

    private Predicate buildTagsPredicate(List<String> tagNames, CriteriaBuilder criteriaBuilder,
                                         Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        tagNames.forEach(tagName -> {
            ListJoin<GiftCertificate, Tag> tags = root.joinList("associatedTags");
            predicates.add(criteriaBuilder.equal(tags.get("name"), tagName));
        });
        return criteriaBuilder.and(toPredicatesArray(predicates));
    }

    private List<Order> buildSortingCriteria(QueryFiltersConfig config, CriteriaBuilder criteriaBuilder,
                                             Root<GiftCertificate> root) {
        List<Order> sortOrders = new ArrayList<>();
        if (config.hasSortParameters()) {
            Map<SortAttribute, SortDirection> sorts = config.getSortParameters();
            sorts.forEach((key, value) -> {
                Expression<GiftCertificate> expression = root.get(key.getAttributeName());
                if (value == SortDirection.ASC) {
                    sortOrders.add(criteriaBuilder.asc(expression));
                } else {
                    sortOrders.add(criteriaBuilder.desc(expression));
                }
            });
        }
        return sortOrders;
    }

    private Predicate[] toPredicatesArray(List<Predicate> predicates) {
        Predicate[] predicatesArray = new Predicate[predicates.size()];
        for (int i = 0; i < predicates.size(); i++) {
            predicatesArray[i] = predicates.get(i);
        }
        return predicatesArray;
    }

    @Override
    public List<Tag> findAssociatedTags(Long certificateId) {
        return new ArrayList<>(entityManager.find(GiftCertificate.class, certificateId).getAssociatedTags());
    }
}
