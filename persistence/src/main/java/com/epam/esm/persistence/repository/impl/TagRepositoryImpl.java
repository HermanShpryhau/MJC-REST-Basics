package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.persistence.repository.CustomTagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepositoryImpl implements CustomTagRepository {
    /**
     * Query to select most widely used tag(s) of a user with the highest cost of all orders.
     */
    private static final String MOST_POPULAR_QUERY = "SELECT Tag.id, Tag.name FROM Tag\n" +
            "JOIN Gift_certificate_has_Tag GchT on Tag.id = GchT.tag AND GchT.certificate IN (SELECT Gift_certificate" +
            ".id FROM Gift_certificate\n" +
            "JOIN Orders O on Gift_certificate.id = O.certificate_id AND O.user_id = (\n" +
            "        SELECT User.id FROM User\n" +
            "        JOIN Orders O on User.id = O.user_id\n" +
//            "        JOIN Gift_certificate Gc on Gc.id = O.certificate_id\n" +
            "        GROUP BY User.id\n" +
            "        ORDER BY SUM(O.total_price) DESC\n" +
            "        LIMIT 1\n" +
            "    ))\n" +
            "GROUP BY GchT.tag\n" +
            "ORDER BY COUNT(GchT.certificate) DESC";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findMostPopularTag() {
        Query nativeQuery = entityManager.createNativeQuery(MOST_POPULAR_QUERY, Tag.class);
        List<Tag> result = new ArrayList<>();
        nativeQuery.getResultStream()
                .filter(Tag.class::isInstance)
                .forEach(obj -> result.add((Tag) obj));
        return result;
    }
}
