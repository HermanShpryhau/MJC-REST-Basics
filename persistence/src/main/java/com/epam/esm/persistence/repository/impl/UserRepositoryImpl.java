package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.User;
import com.epam.esm.persistence.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String SELECT_ALL_QUERY = "SELECT user FROM User user";

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
}
