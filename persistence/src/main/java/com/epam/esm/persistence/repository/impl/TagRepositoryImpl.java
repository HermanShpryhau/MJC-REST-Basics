package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.RepositoryException;
import com.epam.esm.persistence.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String SELECT_ALL_QUERY = "SELECT tag FROM Tag tag";
    private static final String SELECT_BY_NAME_QUERY = "SELECT tag FROM Tag tag WHERE tag.name=:tagName";

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
    public Tag update(Tag entity) throws RepositoryException {
        throw new RepositoryException("Unsupported operation.");
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
}
