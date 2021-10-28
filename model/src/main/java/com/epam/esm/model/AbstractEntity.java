package com.epam.esm.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {
    protected static final String CREATE_OPERATION = "CREATE";
    protected static final String UPDATE_OPERATION = "UPDATE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "operation_timestamp")
    private Timestamp operationTimestamp;

    protected AbstractEntity() {
    }

    protected AbstractEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Timestamp getOperationTimestamp() {
        return operationTimestamp;
    }

    public void setOperationTimestamp(Timestamp operationTimestamp) {
        this.operationTimestamp = operationTimestamp;
    }

    @PrePersist
    public void onPrePersist() {
        auditOperation(CREATE_OPERATION);
    }

    @PreUpdate
    public void onPreUpdate() {
        auditOperation(UPDATE_OPERATION);
    }

    private void auditOperation(String operation) {
        setOperation(operation);
        setOperationTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (!id.equals(that.id)) return false;
        if (!operation.equals(that.operation)) return false;
        return operationTimestamp.equals(that.operationTimestamp);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + operation.hashCode();
        result = 31 * result + operationTimestamp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", operationTimestamp=" + operationTimestamp +
                '}';
    }
}
