package com.epam.esm.persistence.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.RepositoryErrorCode;
import com.epam.esm.persistence.repository.RepositoryException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private static final String SELECT_ALL_QUERY = "SELECT order FROM Order order";
    private static final String COUNT_ALL_QUERY = "SELECT COUNT(order) FROM Order order";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order save(Order entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<Order> findAll(int page, int size) {
        return entityManager.createQuery(SELECT_ALL_QUERY, Order.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public Order update(Order entity) {
        return entityManager.merge(entity);
    }

    @Override
    public boolean delete(Long id) {
        Order order = Optional.ofNullable(entityManager.find(Order.class, id))
                .orElseThrow(() -> new RepositoryException(RepositoryErrorCode.ORDER_NOT_FOUND, id));
        entityManager.remove(order);
        return true;
    }

    @Override
    public int countAll() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT_ALL_QUERY, Long.class);
        return query.getSingleResult().intValue();
    }
}
