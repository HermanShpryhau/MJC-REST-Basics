package com.epam.esm.persistence.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.persistence.repository.ColumnName;
import com.epam.esm.persistence.repository.RepositoryException;
import com.epam.esm.persistence.repository.TagRepository;
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
public class TagRepositoryImpl implements TagRepository {
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Tag";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Tag WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM Tag WHERE id=?";
    private static final String SELECT_ASSOCIATED_CERTIFICATES =
            "SELECT * FROM Gift_certificate\n" +
                    "JOIN Gift_certificate_has_Tag GchT on Gift_certificate.id = GchT.certificate\n" +
                    "WHERE GchT.tag=?";
    private static final int MIN_AFFECTED_ROWS = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Tag> tagRowMapper;
    private final RowMapper<GiftCertificate> certificateRowMapper;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Tag> tagRowMapper,
                             RowMapper<GiftCertificate> certificateRowMapper,
                             @Qualifier("TagJdbcInsert") SimpleJdbcInsert jdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
        this.certificateRowMapper = certificateRowMapper;
        this.jdbcInsert = jdbcInsert;
    }

    @Override
    public Tag save(Tag entity) {
        Number id = jdbcInsert.executeAndReturnKey(buildParametersMap(entity));
        entity.setId(id.longValue());
        return entity;
    }

    private Map<String, Object> buildParametersMap(Tag entity) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ColumnName.NAME_COLUMN, entity.getName());
        return parameters;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, tagRowMapper);
    }

    @Override
    public Tag findById(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID_QUERY, tagRowMapper, id).stream().findAny().orElse(null);
    }

    @Override
    public Tag update(Long id, Tag entity) throws RepositoryException {
        throw new RepositoryException("Unsupported operation.");
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_QUERY, id) == MIN_AFFECTED_ROWS;
    }

    @Override
    public List<GiftCertificate> findAssociatedGiftCertificates(Long tagId) {
        return jdbcTemplate.query(SELECT_ASSOCIATED_CERTIFICATES, certificateRowMapper, tagId);
    }
}
