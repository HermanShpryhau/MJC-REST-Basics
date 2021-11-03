package com.epam.esm.persistence.repository.audit;

import com.epam.esm.model.AbstractEntity;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AuditedAspect {
    private static final String AUDIT_TABLE_NAME = "Audit";
    private static final String ID_COLUMN = "id";
    private static final String TABLE_NAME_COLUMN = "table_name";
    private static final String ENTITY_ID_COLUMN = "entity_id";
    private static final String OPERATION_COLUMN = "operation";

    private final SimpleJdbcInsert insert;

    @Autowired
    public AuditedAspect(JdbcTemplate jdbcTemplate) {
        this.insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(AUDIT_TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN)
                .usingColumns(TABLE_NAME_COLUMN, ENTITY_ID_COLUMN, OPERATION_COLUMN);
    }

    @Pointcut("execution(* com.epam.esm.persistence.repository.*.*(..))")
    public void persistenceMethod() {}

    @Pointcut("@annotation(audited)")
    public void auditedOperation(Audited audited) {}

    @AfterReturning(value = "persistenceMethod() && args(com.epam.esm.model.AbstractEntity,..) && auditedOperation(audited)",
            returning = "returnedEntity", argNames = "audited,returnedEntity")
    public void beforeDataAccessOperationOnEntity(Audited audited, AbstractEntity returnedEntity) {
        Map<String, Object> parameters = buildParametersMap(returnedEntity.getId(), audited);
        insert.execute(parameters);
    }

    @Before(value = "persistenceMethod() && args(id, ..) && auditedOperation(audited)", argNames = "id,audited")
    public void beforeDataAccessOperationById(Long id, Audited audited) {
        Map<String, Object> parameters = buildParametersMap(id, audited);
        insert.execute(parameters);
    }

    private Map<String, Object> buildParametersMap(Long id, Audited audited) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TABLE_NAME_COLUMN, audited.table());
        parameters.put(ENTITY_ID_COLUMN, id);
        parameters.put(OPERATION_COLUMN, audited.operation());
        return parameters;
    }
}
