package com.epam.esm.model.audit;

import com.epam.esm.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

@Component
public class OrderAuditingListener extends AuditingListener {
    private static final String TABLE_NAME = "Orders";

    @Autowired
    public OrderAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TABLE_NAME);
    }

    @PostPersist
    public void onPersist(Order entity) {
        auditOperation(CREATE_OPERATION, entity.getId());
    }

    @PostUpdate
    public void onUpdate(Order entity) {
        auditOperation(UPDATE_OPERATION, entity.getId());
    }

    @PreRemove
    public void onDelete(Order entity) {
        auditOperation(DELETE_OPERATION, entity.getId());
    }
}
