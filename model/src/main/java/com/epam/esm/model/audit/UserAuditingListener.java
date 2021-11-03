package com.epam.esm.model.audit;

import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

@Component
public class UserAuditingListener extends AuditingListener {
    private static final String TABLE_NAME = "User";

    @Autowired
    public UserAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TABLE_NAME);
    }

    @PostPersist
    public void onPersist(User entity) {
        auditOperation(CREATE_OPERATION, entity.getId());
    }

    @PostUpdate
    public void onUpdate(User entity) {
        auditOperation(UPDATE_OPERATION, entity.getId());
    }

    @PreRemove
    public void onDelete(User entity) {
        auditOperation(DELETE_OPERATION, entity.getId());
    }
}
