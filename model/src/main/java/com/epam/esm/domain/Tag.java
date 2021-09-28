package com.epam.esm.domain;

import java.io.Serializable;

public class Tag extends AbstractEntity implements Serializable {
    private String name;

    public Tag() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;

        Tag other = (Tag) o;
        if (!getId().equals(other.getId())) return false;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
