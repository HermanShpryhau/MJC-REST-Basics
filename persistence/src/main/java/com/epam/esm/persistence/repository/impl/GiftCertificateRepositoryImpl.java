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
    private static final String SELECT_BY_NAME_OR_DESCRIPTION_QUERY =
            "SELECT * FROM Gift_certificate\n" +
                    "WHERE name LIKE CONCAT('%', ?, '%') OR description LIKE CONCAT('%', ?, '%')";
    private static final String UPDATE_QUERY =
            "UPDATE Gift_certificate SET " +
                    "name=?, description=?, price=?, duration=?, last_update_date=? " +
                    "WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM Gift_certificate WHERE id=?";
    private static final String SELECT_ASSOCIATED_TAGS_QUERY =
            "SELECT * FROM Tag\n" +
                    "JOIN Gift_certificate_has_Tag GchT on Tag.id = GchT.tag\n" +
                    "WHERE GchT.certificate=?";
    private static final String SELECT_BY_TAG_NAME_QUERY =
            "SELECT * FROM Gift_certificate\n" +
                    "JOIN Gift_certificate_has_Tag GchT on Gift_certificate.id = GchT.certificate\n" +
                    "JOIN Tag T on T.id = GchT.tag\n" +
                    "WHERE T.name='Tag 2'";
    private static final String ADD_TAG_ASSOCIATION_QUERY =
            "INSERT INTO Gift_certificate_has_Tag (certificate, tag) VALUES(?, ?)";
    private static final String REMOVE_TAG_ASSOCIATION_QUERY =
            "DELETE FROM Gift_certificate_has_Tag WHERE certificate=? AND tag=?";

    private static final int MIN_AFFECTED_ROWS = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<GiftCertificate> certificateRowMapper;
    private final RowMapper<Tag> tagRowMapper;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate,
                                         RowMapper<GiftCertificate> certificateRowMapper,
                                         RowMapper<Tag> tagRowMapper,
                                         @Qualifier("GiftCertificateJdbcInsert") SimpleJdbcInsert jdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateRowMapper = certificateRowMapper;
        this.tagRowMapper = tagRowMapper;
        this.jdbcInsert = jdbcInsert;
    }

    @Override
    public GiftCertificate save(GiftCertificate entity) {
        Map<String, Object> parameters = buildParametersMap(entity);
        Number id = jdbcInsert.executeAndReturnKey(parameters);
        return findById(id.longValue());
    }

    private Map<String, Object> buildParametersMap(GiftCertificate entity) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ColumnName.NAME_COLUMN, entity.getName());
        parameters.put(ColumnName.DESCRIPTION_COLUMN, entity.getDescription());
        parameters.put(ColumnName.PRICE_COLUMN, entity.getPrice());
        parameters.put(ColumnName.DURATION_COLUMN, entity.getDuration());
        return parameters;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, certificateRowMapper);
    }

    @Override
    public GiftCertificate findById(Long id) {
        List<GiftCertificate> certificates = jdbcTemplate.query(SELECT_BY_ID_QUERY, certificateRowMapper, id);
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

    @Override
    public List<GiftCertificate> findByPatternInNameOrDescription(String pattern) {
        return jdbcTemplate.query(SELECT_BY_NAME_OR_DESCRIPTION_QUERY, certificateRowMapper, pattern, pattern);
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        return jdbcTemplate.query(SELECT_BY_TAG_NAME_QUERY, certificateRowMapper, tagName);
    }

    @Override
    public List<Tag> findAssociatedTags(Long certificateId) {
        return jdbcTemplate.query(SELECT_ASSOCIATED_TAGS_QUERY, tagRowMapper, certificateId);
    }

    @Override
    public void addTagAssociation(Long certificateId, Long tagId) {
        jdbcTemplate.update(ADD_TAG_ASSOCIATION_QUERY, certificateId, tagId);
    }

    @Override
    public void removeTagAssociation(Long certificateId, Long tagId) {
        jdbcTemplate.update(REMOVE_TAG_ASSOCIATION_QUERY, certificateId, tagId);
    }
}
