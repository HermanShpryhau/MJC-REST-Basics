package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT * FROM Gift_certificate";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Gift_certificate WHERE id=?";
    private static final String UPDATE_QUERY =
            "UPDATE Gift_certificate SET " +
                    "name=?, description=?, price=?, duration=?, last_update_date=? " +
                    "WHERE id=?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM Gift_certificate WHERE id=?";
    private static final String NAME_COLUMN = "name";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String PRICE_COLUMN = "price";
    private static final String DURATION_COLUMN = "duration";
    private static final String CREATE_DATE_COLUMN = "create_date";
    private static final String LAST_UPDATE_DATE_COLUMN = "last_update_date";
    private static final int MIN_AFFECTED_ROWS = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<GiftCertificate> mapper;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<GiftCertificate> mapper, @Qualifier("GiftCertificateJdbcInsert") SimpleJdbcInsert jdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.jdbcInsert = jdbcInsert;
    }

    @Override
    public GiftCertificate save(GiftCertificate entity) throws RepositoryException {
        Map<String, Object> parameters = buildParametersMap(entity);
        Number id = jdbcInsert.executeAndReturnKey(parameters);
        entity.setId(id.longValue());
        return entity;
    }

    private Map<String, Object> buildParametersMap(GiftCertificate entity) throws RepositoryException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(NAME_COLUMN, entity.getName());
        parameters.put(DESCRIPTION_COLUMN, entity.getDescription());
        parameters.put(PRICE_COLUMN, entity.getPrice());
        parameters.put(DURATION_COLUMN, entity.getDuration());
        parameters.put(CREATE_DATE_COLUMN, entity.getCreateDate());
        parameters.put(LAST_UPDATE_DATE_COLUMN, entity.getLastUpdateDate());
        return parameters;
    }

    @Override
    public List<GiftCertificate> findAll() throws RepositoryException {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, mapper);
    }

    @Override
    public GiftCertificate findById(Long id) throws RepositoryException {
        List<GiftCertificate> certificates = jdbcTemplate.query(SELECT_BY_ID_QUERY, mapper, id);
        return certificates.stream().findAny().orElse(null);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate entity) throws RepositoryException {
        jdbcTemplate.update(UPDATE_QUERY,
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDuration(),
                entity.getCreateDate(),
                entity.getLastUpdateDate()
        );
        return entity;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        return jdbcTemplate.update(DELETE_BY_ID_QUERY, id) == MIN_AFFECTED_ROWS;
    }
}
