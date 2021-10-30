package com.epam.esm.model;

import com.epam.esm.model.validation.ValidationErrorCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tag")
public class Tag extends AbstractEntity {
    @Column(name = "name")
    @NotNull(message = ValidationErrorCode.TAG_NAME_NOT_NULL)
    @Size(min = 1, max = 45, message = ValidationErrorCode.INVALID_TAG_NAME)
    private String name;

    @ManyToMany(mappedBy = "associatedTags")
    private List<GiftCertificate> associatedCertificates = new ArrayList<>();

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GiftCertificate> getAssociatedCertificates() {
        return associatedCertificates;
    }

    public void setAssociatedCertificates(List<GiftCertificate> associatedCertificates) {
        this.associatedCertificates = associatedCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;

        Tag tag = (Tag) o;

        if (!name.equals(tag.name)) return false;
        return associatedCertificates.equals(tag.associatedCertificates);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + associatedCertificates.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
