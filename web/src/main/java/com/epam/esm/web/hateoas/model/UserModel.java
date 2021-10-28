package com.epam.esm.web.hateoas.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "user", collectionRelation = "users")
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String name;

    public UserModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!super.equals(o)) return false;

        UserModel model = (UserModel) o;

        if (!id.equals(model.id)) return false;
        return name.equals(model.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
