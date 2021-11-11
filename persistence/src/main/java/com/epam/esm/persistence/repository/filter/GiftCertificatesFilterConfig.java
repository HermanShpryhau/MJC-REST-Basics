package com.epam.esm.persistence.repository.filter;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GiftCertificatesFilterConfig {
    private final List<String> tags;
    private final Map<SortAttribute, SortDirection> sortParameters;
    private final String searchPattern;

    private GiftCertificatesFilterConfig(List<String> tags, Map<SortAttribute, SortDirection> sortParameters,
                                         String searchPattern) {
        this.tags = tags;
        this.sortParameters = sortParameters;
        this.searchPattern = searchPattern;
    }

    public List<String> getTags() {
        return tags;
    }

    public Map<SortAttribute, SortDirection> getSortParameters() {
        return sortParameters;
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public boolean hasTags() {
        return !tags.isEmpty();
    }

    public boolean hasSortParameters() {
        return !sortParameters.isEmpty();
    }

    public boolean hasSearchPattern() {
        return searchPattern != null;
    }

    public CriteriaQuery<GiftCertificate> asCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        Predicate[] predicatesArray = buildPredicates(criteriaBuilder, root);

        List<Order> sortOrders = buildSortingCriteria(criteriaBuilder, root);

        criteriaQuery.orderBy(sortOrders).select(root).where(predicatesArray);
        return criteriaQuery;
    }

    private Predicate[] buildPredicates(CriteriaBuilder criteriaBuilder,
                                        Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (hasSearchPattern()) {
            predicates.add(buildSearchPatternPredicate(criteriaBuilder, root));
        }
        if (hasTags()) {
            predicates.add(buildTagsPredicate(criteriaBuilder, root));
        }
        return toPredicatesArray(predicates);
    }

    private Predicate buildSearchPatternPredicate(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        return criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), "%" + searchPattern + "%"),
                criteriaBuilder.like(root.get("description"), "%" + searchPattern + "%")
        );
    }

    private Predicate buildTagsPredicate(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        tags.forEach(tagName -> {
            SetJoin<GiftCertificate, Tag> tagsJoin = root.joinSet("associatedTags");
            predicates.add(criteriaBuilder.equal(tagsJoin.get("name"), tagName));
        });
        return criteriaBuilder.and(toPredicatesArray(predicates));
    }

    private List<Order> buildSortingCriteria(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        List<Order> sortOrders = new ArrayList<>();
        if (hasSortParameters()) {
            sortParameters.forEach((key, value) -> {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> tags = new ArrayList<>();
        private final Map<SortAttribute, SortDirection> sortParameters = new EnumMap<>(SortAttribute.class);
        private String searchPattern;

        public Builder withTags(List<String> tagsList) {
            tags = tagsList;
            return this;
        }

        public Builder withSort(SortAttribute column, SortDirection direction) {
            sortParameters.put(column, direction);
            return this;
        }

        public Builder withSearchPattern(String searchPattern) {
            this.searchPattern = searchPattern;
            return this;
        }

        public GiftCertificatesFilterConfig build() {
            return new GiftCertificatesFilterConfig(tags, sortParameters, searchPattern);
        }
    }
}
