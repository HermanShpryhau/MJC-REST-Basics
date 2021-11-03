package com.epam.esm.model.audit;

import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

@Component
public class TagAuditingListener extends AuditingListener {
    private static final String TABLE_NAME = "Tag";

    @Autowired
    public TagAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TABLE_NAME);
    }

    @PostPersist
    public void onPersist(Tag entity) {
        auditOperation(CREATE_OPERATION, entity.getId());
    }

    @PostUpdate
    public void onUpdate(Tag entity) {
        auditOperation(UPDATE_OPERATION, entity.getId());
    }

    @PreRemove
    public void onDelete(Tag entity) {
        auditOperation(DELETE_OPERATION, entity.getId());
    }
}
