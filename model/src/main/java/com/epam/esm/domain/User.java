package com.epam.esm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "User")
public class User extends AbstractEntity {
    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Order> orders;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        return orders.equals(user.orders);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + orders.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
}
