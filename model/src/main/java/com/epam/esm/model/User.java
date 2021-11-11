package com.epam.esm.model;

import com.epam.esm.model.audit.UserAuditingListener;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
//@EntityListeners(UserAuditingListener.class)
public class User implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Order> orders = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
