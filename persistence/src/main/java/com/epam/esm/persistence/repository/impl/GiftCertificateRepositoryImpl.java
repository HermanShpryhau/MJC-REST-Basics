package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String SELECT_ALL_QUERY = "SELECT giftCertificate from GiftCertificate giftCertificate";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<GiftCertificate> certificateRowMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<GiftCertificate> certificateRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateRowMapper = certificateRowMapper;
    }

    @Override
    public GiftCertificate save(GiftCertificate entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return entityManager
                .createQuery(SELECT_ALL_QUERY, GiftCertificate.class)
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
    public List<GiftCertificate> findWithFilters(QueryFiltersConfig config) {
        PreparedStatementCreator statementCreator = new GiftCertificatePreparedStatementCreator(config);
        return jdbcTemplate.query(statementCreator, certificateRowMapper);
    }

    @Override
    public List<Tag> findAssociatedTags(Long certificateId) {
        return new ArrayList<>(entityManager.find(GiftCertificate.class, certificateId).getAssociatedTags());
    }
}
