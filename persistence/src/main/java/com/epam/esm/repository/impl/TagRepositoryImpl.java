package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.RepositoryException;
import com.epam.esm.repository.TagRepository;
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
    private static final String NAME_COLUMN = "name";
    private static final int MIN_AFFECTED_ROWS = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Tag> mapper;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Tag> mapper, @Qualifier("TagJdbcInsert") SimpleJdbcInsert jdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.jdbcInsert = jdbcInsert;
    }

    @Override
    public Tag save(Tag entity) throws RepositoryException {
        Number id = jdbcInsert.executeAndReturnKey(buildParametersMap(entity));
        entity.setId(id.longValue());
        return entity;
    }

    private Map<String, Object> buildParametersMap(Tag entity) throws RepositoryException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(NAME_COLUMN, entity.getName());
        return parameters;
    }

    @Override
    public List<Tag> findAll() throws RepositoryException {
        return jdbcTemplate.query(SELECT_ALL_QUERY, mapper);
    }

    @Override
    public Tag findById(Long id) throws RepositoryException {
        return jdbcTemplate.query(SELECT_BY_ID_QUERY, mapper, id).stream().findAny().orElse(null);
    }

    @Override
    public Tag update(Long id, Tag entity) throws RepositoryException {
        throw new RepositoryException("Unsupported operation.");
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        return jdbcTemplate.update(DELETE_QUERY, id) == MIN_AFFECTED_ROWS;
    }
}
