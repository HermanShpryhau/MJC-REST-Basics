package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.User;
import com.epam.esm.persistence.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String SELECT_ALL_QUERY = "SELECT user FROM User user";
    private static final String COUNT_ALL_QUERY = "SELECT COUNT(user) FROM User user";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(int page, int size) {
        return entityManager.createQuery(SELECT_ALL_QUERY, User.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public int countAll() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT_ALL_QUERY, Long.class);
        return query.getSingleResult().intValue();
    }
}
