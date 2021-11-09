package com.epam.esm.model.audit;

import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

@Component
public class GiftCertificateAuditingListener extends AuditingListener {
    private static final String TABLE_NAME = "Gift_certificate";

    @Autowired
    public GiftCertificateAuditingListener(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TABLE_NAME);
    }

    @PostPersist
    public void onPersist(GiftCertificate entity) {
        auditOperation(CREATE_OPERATION, entity.getId());
    }

    @PostUpdate
    public void onUpdate(GiftCertificate entity) {
        auditOperation(UPDATE_OPERATION, entity.getId());
    }

    @PreRemove
    public void onDelete(GiftCertificate entity) {
        auditOperation(DELETE_OPERATION, entity.getId());
    }
}
