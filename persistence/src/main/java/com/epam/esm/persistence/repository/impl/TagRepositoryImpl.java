package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String SELECT_ALL_QUERY = "SELECT tag FROM Tag tag";
    private static final String SELECT_BY_NAME_QUERY = "SELECT tag FROM Tag tag WHERE tag.name=:tagName";
    private static final String COUNT_ALL_QUERY = "SELECT COUNT(tag) FROM Tag tag";

    /**
     * Query to select most widely used tag(s) of a user with the highest cost of all orders.
     */
    private static final String MOST_POPULAR_QUERY = "SELECT Tag.id, Tag.name FROM Tag\n" +
            "JOIN Gift_certificate_has_Tag GchT on Tag.id = GchT.tag AND GchT.certificate IN (SELECT Gift_certificate" +
            ".id FROM Gift_certificate\n" +
            "JOIN Orders O on Gift_certificate.id = O.certificate_id AND O.user_id = (\n" +
            "        SELECT User.id FROM User\n" +
            "        JOIN Orders O on User.id = O.user_id\n" +
            "        JOIN Gift_certificate Gc on Gc.id = O.certificate_id\n" +
            "        GROUP BY User.id\n" +
            "        HAVING SUM(O.total_price)\n" +
            "        ORDER BY SUM(O.total_price) DESC\n" +
            "        LIMIT 1\n" +
            "    ))\n" +
            "GROUP BY GchT.tag\n" +
            "HAVING COUNT(GchT.certificate)\n" +
            "ORDER BY COUNT(GchT.certificate) DESC";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag save(Tag entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<Tag> findAll(int page, int size) {
        return entityManager.createQuery(SELECT_ALL_QUERY, Tag.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery(SELECT_BY_NAME_QUERY, Tag.class);
        query.setParameter("tagName", name);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<Tag> findMostPopularTag() {
        Query nativeQuery = entityManager.createNativeQuery(MOST_POPULAR_QUERY, Tag.class);
        List<Tag> result = new ArrayList<>();
        nativeQuery.getResultStream()
                .filter(Tag.class::isInstance)
                .forEach(obj -> result.add((Tag) obj));
        return result;
    }

    @Override
    public boolean delete(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            return false;
        }
        entityManager.remove(tag);
        return true;
    }

    @Override
    public List<GiftCertificate> findAssociatedGiftCertificates(Long tagId) {
        return new ArrayList<>(entityManager.find(Tag.class, tagId).getAssociatedCertificates());
    }

    @Override
    public int countAll() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT_ALL_QUERY, Long.class);
        return query.getSingleResult().intValue();
    }
}
