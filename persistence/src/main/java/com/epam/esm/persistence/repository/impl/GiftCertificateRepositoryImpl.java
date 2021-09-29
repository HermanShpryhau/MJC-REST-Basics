package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.ColumnName;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
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
    public GiftCertificate save(GiftCertificate entity) {
        Map<String, Object> parameters = buildParametersMap(entity);
        Number id = jdbcInsert.executeAndReturnKey(parameters);
        entity.setId(id.longValue());
        return entity;
    }

    private Map<String, Object> buildParametersMap(GiftCertificate entity) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ColumnName.NAME_COLUMN, entity.getName());
        parameters.put(ColumnName.DESCRIPTION_COLUMN, entity.getDescription());
        parameters.put(ColumnName.PRICE_COLUMN, entity.getPrice());
        parameters.put(ColumnName.DURATION_COLUMN, entity.getDuration());
        parameters.put(ColumnName.CREATE_DATE_COLUMN, entity.getCreateDate());
        parameters.put(ColumnName.LAST_UPDATE_DATE_COLUMN, entity.getLastUpdateDate());
        return parameters;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, mapper);
    }

    @Override
    public GiftCertificate findById(Long id) {
        List<GiftCertificate> certificates = jdbcTemplate.query(SELECT_BY_ID_QUERY, mapper, id);
        return certificates.stream().findAny().orElse(null);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate entity) {
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
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_QUERY, id) == MIN_AFFECTED_ROWS;
    }
}
