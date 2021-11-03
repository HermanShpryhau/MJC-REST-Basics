package com.epam.esm.model.audit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.Map;

public abstract class AuditingListener {
    protected static final String AUDIT_TABLE_NAME = "Audit";
    protected static final String ID_COLUMN = "id";
    protected static final String TABLE_NAME_COLUMN = "table_name";
    protected static final String ENTITY_ID_COLUMN = "entity_id";
    protected static final String OPERATION_COLUMN = "operation";
    protected static final String CREATE_OPERATION = "CREATE";
    protected static final String UPDATE_OPERATION = "UPDATE";
    protected static final String DELETE_OPERATION = "DELETE";

    protected final SimpleJdbcInsert jdbcInsert;
    protected final String tableName;

    protected AuditingListener(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AUDIT_TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN)
                .usingColumns(TABLE_NAME_COLUMN, ENTITY_ID_COLUMN, OPERATION_COLUMN);
        this.tableName = tableName;
    }

    protected void auditOperation(String operation, Long id) {
        Map<String, Object> args = getInsertArguments(operation, id);
        jdbcInsert.execute(args);
    }

    private Map<String, Object> getInsertArguments(String operation, Long id) {
        Map<String, Object> args = new HashMap<>();
        args.put(TABLE_NAME_COLUMN, tableName);
        args.put(OPERATION_COLUMN, operation);
        args.put(ENTITY_ID_COLUMN, id);
        return args;
    }
}
