package com.epam.esm.model;

import com.epam.esm.model.audit.TagAuditingListener;
import com.epam.esm.model.validation.ValidationErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tag")
@EntityListeners(TagAuditingListener.class)
public class Tag implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = ValidationErrorCode.TAG_NAME_NOT_NULL)
    @Size(min = 1, max = 45, message = ValidationErrorCode.INVALID_TAG_NAME)
    private String name;

    @ManyToMany(mappedBy = "associatedTags")
    private List<GiftCertificate> associatedCertificates = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
    
    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

